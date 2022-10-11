package com.javachinna.service;


import com.javachinna.model.*;
import com.javachinna.repo.*;
import com.javachinna.sentimentAnalyzer.Sentiment;
import com.javachinna.sentimentAnalyzer.SentimentResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Slf4j

public class PartnerServiceImpl implements IPartnerService{

    @Autowired
    IPartnerRepository partnerrepo;
    @Autowired
    UserRepository userrepo;
    @Autowired
    ICandidacyRepository candirepo;
    @Autowired
    DatabaseFileRepository filerepo;
    @Autowired
    CommentUniversityRepository commentUniversityRepository;
    @Autowired
    CommentUniversityServiceImpl commentService;
    @Autowired
    Sentiment sentimentService;

    @Autowired
    SendEmailService emailService;





    @Override
    public void addPartner(PartnerInstitution partner) {
        partnerrepo.save(partner);
    }




    @Override
    public void deletePartnerById(Integer Id) {
        log.info("Partner deleted ,ID:"+Id);
        partnerrepo.deleteById(Id);

    }

    @Override
    public PartnerInstitution update(PartnerInstitution university) {
        return partnerrepo.save(university);
    }

    @Override
    public boolean existById(Integer idUniversity) {
        return partnerrepo.existsById(idUniversity);
    }


    @Override
    public PartnerInstitution getPartnerById(Integer IdPartner) {
        PartnerInstitution partnerInstitution=partnerrepo.findById(IdPartner).orElse(null);
        return partnerInstitution;
    }



   /* @Override
    public void updatePartner(PartnerInstitution partnerInstitution) {
        partnerrepo.save(partnerInstitution);
    }*/

    @Override
    public List<PartnerInstitution> getAllPartners() {
        return partnerrepo.getAllPartners();
    }

    @Override
    public int getNumberOfStudentDemandsByUniversity(Integer idUniversity) {
        return partnerrepo.getNumberOfStudentDemandsByUniversity(idUniversity);
    }

    @Override
    public PartnerInstitution getUniversityWithTheHighestDemands() {
      return null;


    }


    @Override
    public PartnerInstitution updatePartnerInstitution(PartnerInstitution p,Integer id) {
        PartnerInstitution existingPartner=partnerrepo.findById(id).orElse(null);
        if(p.getName()!=null){
            existingPartner.setName(p.getName());}
        if(p.getCountry()!=null){
            existingPartner.setCountry(p.getCountry());}
        if(p.getGeographicalArea()!=null){
            existingPartner.setGeographicalArea(p.getGeographicalArea());}
        if(p.getLanguage()!=null){
            existingPartner.setLanguage(p.getLanguage());}
        if(p.getEmail()!=null){
            existingPartner.setEmail(p.getEmail());}
        if(p.getPicture()!=null){
            existingPartner.setPicture(p.getPicture());}
        if(String.valueOf(p.getFees())!=null){
            existingPartner.setFees(p.getFees());}
        if(String.valueOf(p.getCapacityReception())!=null){
            existingPartner.setCapacityReception(p.getCapacityReception());}
        if(p.getDescription()!=null){
            existingPartner.setDescription(p.getDescription());}
        if(p.getSpecial()!=null){
            existingPartner.setSpecial(p.getSpecial());}
        if(String.valueOf(p.isActive())!=null){
            existingPartner.setActive(p.isActive());}
        log.info("partner updated"+p.toString());
        return partnerrepo.save(p);

    }

    @Override
    public List<PartnerInstitution> FindAllpartnersByArea(GeographicalArea Area) {
        return partnerrepo.FindAllpartnersByArea(Area);
    }

    @Override
    public List<PartnerInstitution> FindAllpartnersByAreaAndSpecialty(GeographicalArea Area, specialty s) {
        return partnerrepo.FindAllpartnersByAreaAndSpecialty(Area,s);
    }

    @Override
    public double getUniversityRemunerationByDate(Integer idUniversity, Date dateDebut, Date dateFin) {
        return partnerrepo.getUniversityRemunerationByDate(idUniversity,dateDebut,dateFin);
    }

    @Override
    public void affectStudentToPartnerInstitution(Long idUser, Integer idPartner) {
        PartnerInstitution p = partnerrepo.findById(idPartner).orElse(null);
        User student=userrepo.findById(idUser).orElse(null);
        if(p.getUser().getProfession().compareTo(Profession.STUDENT)==0){
            p.setUser(student);
        }
        partnerrepo.save(p);




        //if(p.getUser().getRole().toString()=="STUDENT") {
           // p.setUser(student);
        //}
        //partnerrepo.save(p);

    }




    @Override
    //@Scheduled(cron = "*/30 * * * * *")
    public int numPartnerBySpecialty(specialty s) {
       return partnerrepo.numPartnerBySpecialty(s);

    }

    @Override
    @Transactional
    public void desaffecterPartnerFromAllCandidacy(Integer idUniversity) {


        PartnerInstitution university=partnerrepo.findById(idUniversity).orElse(null);

        List<CandidacyUniversity> listc= (List<CandidacyUniversity>) candirepo.findAll();
        for(CandidacyUniversity c : listc){
            if(c.getPartnerInstitution()==university){
                c.setPartnerInstitution(null);
                candirepo.save(c);
            }
        }
    }

    @Override
    public List<Object> getUniversitiesRemunerationByDateTrie(Date dateDebut, Date dateFin) {
        return partnerrepo.getUniversitiesRemunerationByDateTrie(dateDebut,dateFin);
    }

    @Override
    public int getNumberOfStudentsByUniversity(Integer idUniversity) {
        return partnerrepo.getNumberOfStudentsByUniversity(idUniversity);
    }

    @Override
    public List<PartnerInstitution> getAllUniversityByTopRating() {
        return partnerrepo.getAllUniversityByTopRating();
    }
    @Override
    public int getRemainingCapacityReception(Integer idUniversity) {
        PartnerInstitution university = partnerrepo.findById(idUniversity).orElse(null);
        assert university != null;
        return (university.getCapacityReception()-candirepo.countAcceptedDemandByUniversity(idUniversity));
    }

    @Override
    public boolean checkAvailableUniversity(Integer IdUniversity) {
        PartnerInstitution university = partnerrepo.findById(IdUniversity).orElse(null);
        if (getRemainingCapacityReception(IdUniversity) >= 0   ){
            assert university != null;
            university.setActive(true);
            university.setCapacityReception(getRemainingCapacityReception(IdUniversity));
            partnerrepo.save(university);
            return true;
        }
        else {
            assert university != null;
            university.setActive(false);
            university.setCapacityReception(0);
            partnerrepo.save(university);
            return false;
        }
    }

    @Override
    public List<DataPoint> statNumberStudentByUniversity() {
        List<DataPoint> list = new ArrayList<DataPoint>();
        for(Object object:partnerrepo.numberStudentsByUniversity()){
            DataPoint dataPoint = new DataPoint();
            dataPoint.setLabel((((Object[]) object)[0]).toString());
            dataPoint.setY(Float.valueOf((((Object[]) object)[1]).toString()));
            list.add(dataPoint);
        }
        return list;
    }

    @Override
    public List<DataPoint> statNumberCommentsByUniversity() {
        List<DataPoint> list = new ArrayList<DataPoint>();
        for(Object object:partnerrepo.numberCommentsByUniversity()){
            DataPoint dataPoint = new DataPoint();
            dataPoint.setLabel((((Object[]) object)[0]).toString());
            dataPoint.setY(Float.valueOf((((Object[]) object)[1]).toString()));
            list.add(dataPoint);
        }
        return list;
    }

    @Override
    public List<DataPoint> statNumberGoodRatingsByUniversity() {
        List<DataPoint> list = new ArrayList<DataPoint>();
        for(Object object:partnerrepo.numberGoodRatingsByUniversity()){
            DataPoint dataPoint = new DataPoint();
            dataPoint.setLabel((((Object[]) object)[0]).toString());
            dataPoint.setY(Float.valueOf((((Object[]) object)[1]).toString()));
            list.add(dataPoint);
        }
        return list;
    }

    @Override
    public List<DataPoint> statNumberBadRatingsByUniversity() {
        List<DataPoint> list = new ArrayList<DataPoint>();
        for(Object object:partnerrepo.numberBadRatingsByUniversity()){
            DataPoint dataPoint = new DataPoint();
            dataPoint.setLabel((((Object[]) object)[0]).toString());
            dataPoint.setY(Float.valueOf((((Object[]) object)[1]).toString()));
            list.add(dataPoint);
        }
        return list;
    }



    @Override
    public Map<String, Double> PercentageUniversitiesByArea() {
        Map<String,Double> Percentages=new HashMap<>();
        List<Double> percent = new ArrayList<>();
        double AFRICA=0;
        double AMERICA=0;
        double ASIA=0;
        double EUROPE=0;
        double Oceania=0;
        List<PartnerInstitution> partnerInstitutions=  (List<PartnerInstitution>) partnerrepo.findAll();

        for(PartnerInstitution university : partnerInstitutions){

            if(university.getGeographicalArea().equals(GeographicalArea.AMERICA)){
                AMERICA++;
            }
            else if (university.getGeographicalArea().equals(GeographicalArea.AFRICA)){
                AFRICA++;
            }else if (university.getGeographicalArea().equals(GeographicalArea.ASIA)){
                ASIA++;
            }else if (university.getGeographicalArea().equals(GeographicalArea.EUROPE)){
                EUROPE++;
            }else if(university.getGeographicalArea().equals(GeographicalArea.Oceania)){
                Oceania++;
            }
        }
        if(partnerInstitutions.size()!=0){
            System.out.println("Number Universities:"+partnerInstitutions.size());
            AFRICA =  ((AFRICA/(partnerInstitutions.size()))*100);
            AMERICA= ((AMERICA/(partnerInstitutions.size()))*100);
            ASIA=((ASIA/(partnerInstitutions.size()))*100);
            EUROPE=((EUROPE/(partnerInstitutions.size()))*100);
            Oceania=((Oceania/(partnerInstitutions.size()))*100);
        }

        percent.add(AFRICA);
        percent.add(AMERICA);
        percent.add(ASIA);
        percent.add(EUROPE);
        percent.add(Oceania);

        Percentages.put("AFRICA",AFRICA);
        Percentages.put("AMERICA",AMERICA);
        Percentages.put("ASIA",ASIA);
        Percentages.put("EUROPE",EUROPE);
        Percentages.put("Oceania",Oceania);

        return Percentages;
    }

    @Override
    @Transactional
    public List<PartnerInstitution> findUniversityByFilter(String keyword) {
        return partnerrepo.findUniversityByFilter1(keyword);
    }

    @Override
    public void checkAllCommentsByUniversity(Integer idUniversity) {
        PartnerInstitution university = partnerrepo.findById(idUniversity).orElse(null);
        List<CommentUniversity>commentUniversities=commentUniversityRepository.findByUniversityId(idUniversity);

        assert university != null;
        /*SentimentResult text =sentimentService.analyze(String.valueOf(university.getCommentUniversities()));
        String text1 = "this added comments :\n"+ text+"\n"+" " +
                "has Score : "+text.getScore()+"\n"+" "+
                "and State : "+text.getState()+"\n"+" "+
                "and Detected words are : \n "+text.getDetectedWords()+"\n";
        emailService.sendSimpleEmail(university.getEmail(),"Sentiment Analyzer for all comments for this University", text1);*/


        String sh = "";
        for (CommentUniversity c : commentUniversities){
            sh=" "+c.getComment();
            SentimentResult text =sentimentService.analyze(sh);
            String text1 = "this added comments :\n"+ sh+"\n"+" " +
                    "has Score : "+text.getScore()+"\n"+" "+
                    "and State : "+text.getState()+"\n"+" "+
                    "and Detected words are : \n "+text.getDetectedWords()+"\n";
            emailService.sendSimpleEmail(university.getEmail(),text1, "Sentiment Analyzer for all comments for this University");
        }

    }

    @Override
    public List<PartnerInstitution> SearchMulti(String keyword) {
        return partnerrepo.SearchMulti(keyword);
    }





}
