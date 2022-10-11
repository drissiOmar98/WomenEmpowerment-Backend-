package com.javachinna.service;


import com.javachinna.model.Complaint;
import com.javachinna.model.Topic;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface ITopicService {


    public Topic AddTopic(Topic topic);


   void AddAffectTopicList(Topic topic, Long idUser);


    void deleteTopic(Long idTopic);
    public Topic upDateTopic (Topic topic, Long idUser);
    public List<Topic> getAllTopics();
    Integer getNbrCommentTopic(Long idTopic);

    void TopicWithRate(Long idTopic ,Double rate);

    public List<Topic> SearchTopicMultiple(String key);

    void DeleteTopicAfterfinalDate();


}
