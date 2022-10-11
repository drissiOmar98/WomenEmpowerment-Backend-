package com.javachinna.service;

import com.javachinna.model.*;
import com.javachinna.repo.ITopicRepo;
import com.javachinna.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TopicService implements ITopicService{



    @Autowired
    ITopicRepo iTopicRepo;
    @Autowired
    UserRepository userRepository;


    @Override
    public Topic AddTopic(Topic topic) {
        return iTopicRepo.save(topic);
    }



    @Override
    public void AddAffectTopicList(Topic topic, Long idUser) {

        User user = (User) userRepository.findById(idUser).orElse(null);


                topic.setUser(user);
                iTopicRepo.save(topic);

    }

    @Override
    public Topic upDateTopic(Topic topic, Long idUser) {

        User user =  userRepository.findById(idUser).orElse(null);
        topic.setUser(user);
        return iTopicRepo.save(topic);
    }




    public void deleteTopic(Long idTopic) {
        log.info("In methode deleteTopic");
        log.warn("Are you sure you want to delete topic");

        iTopicRepo.deleteById(idTopic);
        log.error("exeption");

    }

    @Override
    public List<Topic> getAllTopics() {
        return (List<Topic>) iTopicRepo.findAll();
    }


    @Override
    public Integer getNbrCommentTopic(Long idTopic){
        Topic topic = iTopicRepo.findById(idTopic).orElse(null);
        return iTopicRepo.getNbrCommentTopic(idTopic);
    }

    @Override
    public void TopicWithRate(Long idTopic, Double rate) {
        Topic topic = iTopicRepo.findById(idTopic).orElse(null);

        if(topic.getRating()==0)
        {
            topic.setRating(rate);
        }else {
            topic.setRating(((topic.getRating()+rate)/2.0));
        }

        iTopicRepo.save(topic);
    }
////////////////////////SearchMultiple///////////////////
    @Override
    public List<Topic> SearchTopicMultiple(String key) {

        if (key.equals(""))
        {
            return (List<Topic>) iTopicRepo.findAll();
        }else
        {
            return iTopicRepo.searchmultilpltopic(key);
        }

    }
//////////////////////////DeleteAuto/////////////////////////////
@Override
@Scheduled(cron = "0 0/2 * * * *")
public void DeleteTopicAfterfinalDate() {
    LocalDate currentdDate1 =  LocalDate.now();

    ZoneId defaultZoneId = ZoneId.systemDefault();

    Date dd = Date.from(currentdDate1.atStartOfDay(defaultZoneId).toInstant());

    ///pour définir la date__debut mois fin mois//////
    Calendar calLast = Calendar.getInstance();
    Calendar calFirst = Calendar.getInstance();
    calLast.set(Calendar.DATE, calLast.getActualMaximum(Calendar.DATE));
    calFirst.set(Calendar.DATE, calFirst.getActualMinimum(Calendar.DATE));
    Date lastDayOfMonth = calLast.getTime();
    Date firstDayOfMonth = calFirst.getTime();


    for (Topic a :  iTopicRepo.DeleteTopicAfterfinalDate(lastDayOfMonth)
    )
    /*
    {
        ArchiveAppointment ar = new ArchiveAppointment();
        ar.setUsers(a.getUsers());
        ar.setDoctor(a.getDoctor());
        ar.setIdApp(a.getIdApp());
        ar.setRemark(a.getRemark());
        ar.setDateApp(a.getDateApp());
        ar.setDelete_At(new Date());

        iRendezVousArchiveRepos.save(ar);

     */

        iTopicRepo.delete(a);
    }


}

