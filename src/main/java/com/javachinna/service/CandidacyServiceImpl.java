package com.javachinna.service;

import com.javachinna.model.*;
import com.javachinna.repo.DatabaseFileRepository;
import com.javachinna.repo.ICandidacyRepository;
import com.javachinna.repo.IPartnerRepository;
import com.javachinna.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CandidacyServiceImpl implements ICandidacyService{
    private String uploadFolderPath="/Users/Drissi Omar/Desktop/uploadfolder";

    @Autowired
    ICandidacyRepository candidacyRepo;
    @Autowired
    UserRepository userrepo;
    @Autowired
    IPartnerRepository partnerrepo;
    @Autowired
    DatabaseFileRepository filerepo;
    @Autowired
    SendEmailService emailService;
    @Autowired
    SmsService smsService;
    @Autowired
    PartnerServiceImpl partnerService;

    @Autowired
    private SimpMessagingTemplate webSocket;

    private final String  TOPIC_DESTINATION = "/lesson/sms";





    @Override
    @Transactional
        public void addDemandAndAssignToStudent(StatusOfCandidacy status, Date DateOFCandidacy, Long idUser) {

            CandidacyUniversity candidacy=new CandidacyUniversity();
            candidacy.setDateOFCandidacy(DateOFCandidacy);
            candidacy.setStatus(status);


            User student = userrepo.findById(idUser).orElse(null);
            assert student != null;
            int b = student.getProfession().compareTo(Profession.STUDENT);
            if(b==0){
                candidacy.setUser(student);
                candidacyRepo.save(candidacy);
            }

        }
    @Override
    public void deleteCandidacyById(Integer Id) {
        candidacyRepo.deleteById(Id);
    }

    @Override
    public void unassignStudentDemand(Integer id) {
        CandidacyUniversity demand=candidacyRepo.findById(id).orElse(null);
        assert demand!=null;
        int d = demand.getUser().getProfession().compareTo(Profession.STUDENT);
        if(d==0){
            demand.setUser(null);
            candidacyRepo.save(demand);

        }


    }

    @Override
    @Transactional
    public void addDemandAndAssignToStudentAndUniversity(CandidacyUniversity demand,
                                                          Long idUser, Integer idPartner) {


        User student = userrepo.findById(idUser).orElse(null);
        PartnerInstitution university=partnerrepo.findById(idPartner).orElse(null);

        assert student != null;
        assert university!=null;
        int b = student.getProfession().compareTo(Profession.STUDENT);
        int a = userrepo.studentDemands(idUser,idPartner);

        if(b==0 && a <1 && university.isActive()  ){
            Date date = new Date();
            demand.setUser(student);
            demand.setPartnerInstitution(university);
            demand.setStatus(StatusOfCandidacy.WAITING);
            demand.setDateOFCandidacy(date);
            candidacyRepo.save(demand);
            String text = "Hello , your demand :"+demand.getIdCandidacy()+"sent :"+demand.getDateOFCandidacy()+"" +
                    "to the University :"+demand.getPartnerInstitution().getIdPartner()+"have been added Successfully:"+demand.getStatus();
            emailService.sendSimpleEmail(student.getEmail(),text,"Demand Added Successfully");
        }else{
            if(partnerService.getRemainingCapacityReception(idPartner)==0 || !university.isActive()){
                String text = "Hello Sorry , your demand have been deleted";
                emailService.sendSimpleEmail(student.getEmail(),text,"Capacity reception completed ");

            }else if (b!=0) {
                String text = "Hello Sorry , your demand have been deleted";
                emailService.sendSimpleEmail(student.getEmail(),text,"you are not allowed to add demand  ");

            }
            else if (a>=1){
                String text = "Hello Sorry , your demand have been deleted";
                emailService.sendSimpleEmail(student.getEmail(),text,"u can not add two demands for the same university twice  ");
            }




        }

    }



    @Override
    public List<CandidacyUniversity> getAllAcceptedDemandByUniversity(Integer idUniversity, StatusOfCandidacy status) {
        return null;
    }

    @Override
    public int getNbrDemandByUniversity(Integer idUniversity) {
        int nb = 0 ;
        for (CandidacyUniversity demand : candidacyRepo.findAll()){
            if(demand.getPartnerInstitution().getIdPartner()==idUniversity){
                nb++;

            }
        }
        return nb ;
    }

    @Override
    public List<CandidacyUniversity> getdemandsByUniversity(Integer idUniversity) {
        return candidacyRepo.getdemandsByUniversity(idUniversity);
    }

    @Override
    public List<CandidacyUniversity> getdemandsByStudent(Long idStudent) {
        return candidacyRepo.getdemandsByStudent(idStudent);
    }

    @Override
    public List<CandidacyUniversity> getAllDemands() {
        return candidacyRepo.getAllDemands();
    }

    @Override
    public int countAcceptedDemandsByUniversity(Integer idUniversity) {
       int nb =0;
       for (CandidacyUniversity demand: candidacyRepo.findAll()){
           if(demand.getPartnerInstitution().getIdPartner()==idUniversity && demand.getStatus().compareTo(StatusOfCandidacy.ACCEPTED)==0){
               nb++;
           }
       }
       return nb;

    }

    @Override
    public void AcceptDemand(Integer idDemand) {
        CandidacyUniversity demand = candidacyRepo.findById(idDemand).orElse(null);
        assert demand != null;
        PartnerInstitution university = partnerrepo.findById(demand.getPartnerInstitution().getIdPartner()).orElse(null);
        Date date = new Date();

        assert university != null;

        if(partnerService.checkAvailableUniversity(university.getIdPartner())==true){
            demand.setStatus(StatusOfCandidacy.ACCEPTED);
            demand.setDateResponse(date);
            candidacyRepo.save(demand);
            String text = "Hello , your demand :"+demand.getIdCandidacy()+"sent :"+demand.getDateOFCandidacy()+"" +
                    "to the University :"+demand.getPartnerInstitution().getIdPartner()+"have been :"+demand.getStatus();
            emailService.sendSimpleEmail(demand.getUser().getEmail(),text,"Demand Accepted");
            try{
            smsService.send(demand.getUser().getPhoneNumber(),text);
        }
        catch(Exception e){

            webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Error sending the SMS: "+e.getMessage());
            throw e;
        }
        webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS has been sent!: "+demand.getUser().getPhoneNumber());

        }
        else {
            demand.setStatus(StatusOfCandidacy.REFUSED);
            demand.setDateResponse(date);
            candidacyRepo.save(demand);
            String text = "Hello , your demand :"+demand.getIdCandidacy()+"sent :"+demand.getDateOFCandidacy()+"" +
                    "to the University :"+demand.getPartnerInstitution().getIdPartner()+"have been :"+demand.getStatus();
            emailService.sendSimpleEmail(demand.getUser().getEmail(),text,"Demand Refused Capacity completed");

        }

    }

    @Override
    public void accept(CandidacyUniversity d, Integer id) {
        if(this.existId(id)){
            Date date = new Date();
            CandidacyUniversity dd = this.getdemandById(id);
            PartnerInstitution university = partnerrepo.findById(dd.getPartnerInstitution().getIdPartner()).orElse(null);
            assert university != null;

            if(partnerService.checkAvailableUniversity(university.getIdPartner())==true){
                dd.setStatus(StatusOfCandidacy.ACCEPTED);
                dd.setDateResponse(date);
                candidacyRepo.save(dd);
                String text = "Hello , your demand :"+dd.getIdCandidacy()+"sent :"+dd.getDateOFCandidacy()+"" +
                        "to the University :"+dd.getPartnerInstitution().getIdPartner()+"have been :"+dd.getStatus();
                emailService.sendSimpleEmail(dd.getUser().getEmail(),text,"Demand Accepted");
                try{
                    smsService.send(dd.getUser().getPhoneNumber(),text);
                }
                catch(Exception e){

                    webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Error sending the SMS: "+e.getMessage());
                    throw e;
                }
                webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS has been sent!: "+dd.getUser().getPhoneNumber());

            }
            else {
                dd.setStatus(StatusOfCandidacy.REFUSED);
                dd.setDateResponse(date);
                candidacyRepo.save(dd);
                String text = "Hello , your demand :"+dd.getIdCandidacy()+"sent :"+dd.getDateOFCandidacy()+"" +
                        "to the University :"+dd.getPartnerInstitution().getIdPartner()+"have been :"+dd.getStatus();
                emailService.sendSimpleEmail(dd.getUser().getEmail(),text,"Demand Refused Capacity completed");

            }

        }
    }

    private String getTimeStamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }



    @Override
    public void rejectDemand(Integer idDemand) {
        CandidacyUniversity demand = candidacyRepo.findById(idDemand).orElse(null);
        assert demand != null;
        demand.setStatus(StatusOfCandidacy.REFUSED);
        Date date = new Date();
        demand.setDateResponse(date);
        candidacyRepo.save(demand);
        String text = "\n Hello , your demand :"+demand.getIdCandidacy()+"sent :"+demand.getDateOFCandidacy()+"" +
                "to the University :"+demand.getPartnerInstitution().getIdPartner()+"have been :"+demand.getStatus()+"\n";
        emailService.sendSimpleEmail(demand.getUser().getEmail(),text,"Sorry your Demand is  refused");
        /*try{
            smsService.send(demand.getUser().getPhoneNumber(),text);
        }
        catch(Exception e){

            webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Error sending the SMS: "+e.getMessage());
            throw e;
        }
        webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS has been sent!: "+demand.getUser().getPhoneNumber());*/


    }

    @Override
    public List<CandidacyUniversity> demandByDateOfCreation(Date date1, Date date2) {
        return candidacyRepo.demandByDateOfCreation(date1,date2);
    }

    @Override
    public List<CandidacyUniversity> findAllByStatus(StatusOfCandidacy status) {
        return candidacyRepo.findAllByStatus(status);
    }

    @Override
    public List<Object> countDemandsByDate() {
        return candidacyRepo.countDemandsByDate();
    }

    @Override
    public List<Object[]> countAcceptedDemandByDate() {
        return candidacyRepo.countAcceptedDemandByDate();
    }

    @Override
    public List<Object[]> countDemandByUniversity() {
        return candidacyRepo.countDemandByUniversity();
    }

    /*@Override
    public List<Object[]> countNumberStudentPerNationalityByYear(String ch,Date dateDebut, Date dateFin) {
        return candidacyRepo.countNumberStudentPerNationalityByYear(ch,dateDebut,dateFin);
    }*/
    @Override
    public long getTimeAttendForDemandResponse(int idDemand) {
        CandidacyUniversity demand=candidacyRepo.findById(idDemand).orElse(null);
        assert demand != null;
        return Math.abs(demand.getDateResponse().getTime()-demand.getDateOFCandidacy().getTime());
    }

    @Override
    public long getAverageWaitingTimeDemandByUniversity(Integer idUniversity) {
        long total=0;
        List<CandidacyUniversity>candidacyUniversities=candidacyRepo.findAllTreatedByUniversity(idUniversity);
        for(CandidacyUniversity c : candidacyUniversities){
            total+= Math.abs(c.getDateResponse().getTime()-c.getDateOFCandidacy().getTime());
        }
        return total/candidacyUniversities.size();

    }

    @Override
    public void addCvToCandidacy(String id, Integer idDemand) {
        DatabaseFile cv = filerepo.findById(id).orElse(null);
        CandidacyUniversity demand = candidacyRepo.findById(idDemand).orElse(null);
        assert demand != null;
        assert cv != null;
        if(Objects.equals(cv.getUser().getId(), demand.getUser().getId())){
            demand.setFileUpload(cv);
            candidacyRepo.save(demand);
        }

    }

    @Override
    public boolean existId(Integer idDemand) {
        return candidacyRepo.existsById(idDemand);
    }

    @Override
    public CandidacyUniversity getdemandById(Integer IdDemand) {
            CandidacyUniversity demand = candidacyRepo.findById(IdDemand).orElse(null);
            return  demand;
    }


}
