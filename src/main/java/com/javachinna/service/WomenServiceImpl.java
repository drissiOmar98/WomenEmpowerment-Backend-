package com.javachinna.service;


import com.javachinna.model.*;
import com.javachinna.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
@Transactional
public class WomenServiceImpl implements IWomenService {

   @Autowired
   private IReclamtionRepository myReclamationRepository;
   @Autowired
   private IRendezVousRepository myRendezVousRepository;
   @Autowired
   private IRendezVousArchiveRepos iRendezVousArchiveRepos;
   @Autowired
   private UserRepository myUserRepository;
   @Autowired
   private IClinicalRepository myClinicalRepository;
   @Autowired
   private IComplaintResponseRepository myComplaintResponseRepository;
   @Autowired
   private SendEmailService sendEmailService;




    @Override
    public List<Complaint> RetrieveAllComplaints() {
        List<Complaint> rec =(List<Complaint>) myReclamationRepository.findAll();
        return rec;
    }


    @Override
    public void  AddComplaintAndAssignToUser(Complaint c, Long idUser) {
        User user=myUserRepository.findById(idUser).orElse(null);
        c.setUsers(user);
        myReclamationRepository.save(c);
        sendEmailService.sendSimpleEmail("farouk.hajjej@esprit.tn","your complaint is taken care of!","Complaint Response");
    }


    @Override
    public void DeleteComplaint(Long idCom) {
    myReclamationRepository.deleteById(idCom);
    }

    @Override
    public void UpdateComplaint(Complaint c, Long idUser) {
        User user=myUserRepository.findById(idUser).orElse(null);
        c.setUsers(user);

        myReclamationRepository.save(c);
    }

    @Override
    public  Complaint RetrieveComplaint (Long idCom) {
        Complaint c=myReclamationRepository.findById(idCom).orElse(null);

        return c;
    }

    @Override
    public List<ComplaintResponse> RetrieveAllComplaintsResponses() {

         List<ComplaintResponse> response=(List<ComplaintResponse>) myComplaintResponseRepository.findAll();
        return response;


    }
    @Override
    public List<Complaint> SearchComplaintMultiple(String key) {

        if (key.equals(""))
        {
            return (List<Complaint>) myReclamationRepository.findAll();
        }else
        {
            return myReclamationRepository.searchmultilplcomplaint(key);
        }

    }




    @Override
    public void AddComplaintResponseAndAssignToComplaintAndAdmin(ComplaintResponse response, Long idCom, Long idUser) {
        User user=myUserRepository.findById(idUser).orElse(null);
        Complaint c=myReclamationRepository.findById(idCom).orElse(null);
        response.setUsers(user);
        response.setComplaints(c);
        myComplaintResponseRepository.save(response);

    }


    @Override
    public void DeleteComplaintResponse(Long idResp) {
        myComplaintResponseRepository.deleteById(idResp);

    }

    @Override
    public void UpdateComplaintResponse(ComplaintResponse response, Long idCom, Long idUser) {
        User user=myUserRepository.findById(idUser).orElse(null);
        Complaint c=myReclamationRepository.findById(idCom).orElse(null);
        response.setUsers(user);
        response.setComplaints(c);
        myComplaintResponseRepository.save(response);


    }

    @Override
    public Clinical AddClinical(Clinical clinical) {
        return myClinicalRepository.save(clinical);
    }
   // @Transactional
    @Override
    public void AddDoctorAndAssignToClinical(User doctor, Long idClinical) {
        Clinical cl=myClinicalRepository.findById(idClinical).orElse(null);
        cl.getDoctors().add(doctor);
        //doctor.getClinical().add(cl);

         myClinicalRepository.save(cl);
    }

    @Override
    public User AddUser(User user) {
        return myUserRepository.save(user);
    }

    @Override
    public List<Appointment> RetrieveAllAppointments(){
        List<Appointment> ren=(List<Appointment>) myRendezVousRepository.findAll();
        return ren;
    }


    @Override
    public void AddAppointmentAndAssignDoctorAndUser(Appointment re, Long idDoctor, Long idUser){
        User user=myUserRepository.findById(idUser).orElse(null);
        User d=myUserRepository.findById(idDoctor).orElse(null);
        re.setUsers(user);
        re.setDoctor(d);
        sendEmailService.sendSimpleEmail(user.getEmail(),"Your Appointment is taken care of At: " +re.getDateApp()+  "  UserName: "+re.getUsers().getFirstName() +" "+re.getUsers().getLastName() +" And UserName Doctor : " +d.getFirstName()+ " " +d.getLastName()+"","Appointment Response");
        myRendezVousRepository.save(re);

    }

    @Override
    public void DeleteAppointment(Long idApp) {
        myRendezVousRepository.deleteById(idApp);

    }





  ///////////// modif  11:16 /////////////////////////
    @Override
    @Scheduled(cron = "0 0/2 * * * *")
    public void DeleteAppointmentAfterfinalDate() {
        LocalDate currentdDate1 =  LocalDate.now();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd = Date.from(currentdDate1.atStartOfDay(defaultZoneId).toInstant());
        for (Appointment a :  myRendezVousRepository.DeleteAppointmentAfterfinalDate(dd)) {

            ArchiveAppointment ar = new ArchiveAppointment();

            ar.setUsers(a.getUsers());
            ar.setDoctor(a.getDoctor());
            ar.setIdApp(a.getIdApp());
            ar.setRemark(a.getRemark());
            ar.setDateApp(a.getDateApp());
            ar.setDelete_At(new Date());

            iRendezVousArchiveRepos.save(ar);

            myRendezVousRepository.delete(a);
        }


    }

    @Override
    public void UpdateAppointment(Appointment re, Long idUser, Long idDoctor) {
        User user=myUserRepository.findById(idUser).orElse(null);
        User d=myUserRepository.findById(idDoctor).orElse(null);
        re.setUsers(user);
        re.setDoctor(d);
        myRendezVousRepository.save(re);

    }

    @Override
    public  Appointment RetrieveAppointment(Long idApp) {
        Appointment ren=myRendezVousRepository.findById(idApp).orElse(null);
        return ren;
    }

    @Override
    public List<Appointment> getAppointmentByClinicalAndSpeciality(Long idClinical, Profession profession) {
        return myRendezVousRepository.getAppointmentByClinicalAndSpeciality(idClinical, profession);
    }

    @Override
    public int GetNbrAppointmentDoctor(Long idDoctor) {
        return myRendezVousRepository.GetNbrAppointmentDoctor(idDoctor);
    }





    @Override
    public int GetIncomeDoctor(long idDoc, Date startDate, Date endDate) {
        return myRendezVousRepository.GetIncomeDoctor(idDoc,startDate,endDate);
    }


    @Override
    public List<User> ScoreDoctor() {

        List<User> users = (List<User>) myUserRepository.findAll();
        for (User user :users) {
            int Score =GetNbrAppointmentDoctor(user.getId())*10;
            Score+=myRendezVousRepository.GetNbrComplaintDoctor(user.getId())*(-5);
            user.setScore(Score);
        }
        return myUserRepository.classementDoctor();

    }

    @Override
    public Map<String,Double>  PourcentageReclamationByType() {

        Map<String,Double> pourcentages=new HashMap<>();

        double PUBLICATION = 0;
        double TRAINING=0;
        double OFFER=0;
        double CANDIDACY=0;

        List<Complaint> complaints=  (List<Complaint>) myReclamationRepository.findAll();

        System.out.println(complaints);

        for (Complaint complaint: complaints) {

            if (complaint.getType().equals(TypeComplaint.PUBLICATION)) {
                PUBLICATION++;
            }

            else if (complaint.getType().equals(TypeComplaint.TRAINING)) {
                TRAINING++;}

            else if (complaint.getType().equals(TypeComplaint.OFFER)) {
                OFFER++;}

            else if (complaint.getType().equals(TypeComplaint.CANDIDACY)) {
                CANDIDACY++;}
        }
        if (complaints.size() !=0) {

            System.out.println("number_reclamations:"+complaints.size());

            PUBLICATION =  ((PUBLICATION/(complaints.size()))*100);


            TRAINING = ((TRAINING/(complaints.size()))*100);


            OFFER = ((OFFER/(complaints.size()))*100);

            CANDIDACY = ((CANDIDACY/(complaints.size()))*100);
        }

        pourcentages.put("PUBLICATION",PUBLICATION);
        pourcentages.put("TRAINING",TRAINING);
        pourcentages.put("OFFER",OFFER);
        pourcentages.put("CANDIDACY",CANDIDACY);

        /*
        pourcentages.add(PUBLICATION);

        pourcentages.add(TRAINING);

        pourcentages.add(OFFER);
        pourcentages.add(CANDIDACY);

         */

        System.out.println(pourcentages);

        return pourcentages;
    }
}



