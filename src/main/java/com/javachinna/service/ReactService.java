package com.javachinna.service;


import com.javachinna.model.*;
import com.javachinna.repo.CommentUniversityRepository;
import com.javachinna.repo.IPartnerRepository;
import com.javachinna.repo.ReactRepository;
import com.javachinna.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReactService implements IReactService {



    @Autowired
    ReactRepository reactRepository;
    @Autowired
    CommentUniversityRepository commentrepo;
    @Autowired
    IPartnerRepository partnerRepository;

    @Autowired
    UserRepository userRepository;

     public React save(Long id, React react){
         CommentUniversity commentUniversity = commentrepo.findById(id).orElse(null);
          react.setCommentUniversity(commentUniversity);
         assert commentUniversity != null;
         react.setUser(commentUniversity.getUser());
         react.setPartnerInstitution(commentUniversity.getPartnerInstitution());

     return  reactRepository.save(react);
}
    public List<React> findAllByCommentId(Long id){
    return  reactRepository.findAllByCommentId(id);
    }

    @Override
    @Transactional
    public React addReactForUniversity(long id, React react, Integer idUniversity) {
        User student =userRepository.findById(id).orElse(null);
        PartnerInstitution university=partnerRepository.findById(idUniversity).orElse(null);
        react.setUser(student);
        react.setPartnerInstitution(university);
        return  reactRepository.save(react);
    }


    public List<React> findAllByCommentIdAndEmoji(Long idComment, Emoji em){
        return  reactRepository.findAllByCommentIdAndEmoji(idComment, em);
    }

    @Override
    public Long countAllByCommentId(Long idComment) {
        return reactRepository.countAllByCommentId(idComment);
    }

    @Override
    public List<DataPoint> statNumberHappyReactsByUniversity() {
        List<DataPoint> list = new ArrayList<DataPoint>();
        for(Object object:reactRepository.numberHappyReactByUniversity()){
            DataPoint dataPoint = new DataPoint();
            dataPoint.setLabel((((Object[]) object)[0]).toString());
            dataPoint.setY(Float.valueOf((((Object[]) object)[1]).toString()));
            list.add(dataPoint);
        }
        return list;
    }

    @Override
    public List<DataPoint> statNumberAngryReactsByUniversity() {
        List<DataPoint> list = new ArrayList<DataPoint>();
        for(Object object:reactRepository.numberAngryReactByUniversity()){
            DataPoint dataPoint = new DataPoint();
            dataPoint.setLabel((((Object[]) object)[0]).toString());
            dataPoint.setY(Float.valueOf((((Object[]) object)[1]).toString()));
            list.add(dataPoint);
        }
        return list;
    }

    @Override
    public List<DataPoint> statNumberLikeReactsByUniversity() {
        List<DataPoint> list = new ArrayList<DataPoint>();
        for(Object object:reactRepository.numberLikeReactByUniversity()){
            DataPoint dataPoint = new DataPoint();
            dataPoint.setLabel((((Object[]) object)[0]).toString());
            dataPoint.setY(Float.valueOf((((Object[]) object)[1]).toString()));
            list.add(dataPoint);
        }
        return list;
    }

    @Override
    public List<DataPoint> statNumberDislikeReactsByUniversity() {
        List<DataPoint> list = new ArrayList<DataPoint>();
        for(Object object:reactRepository.numberDisLikeReactByUniversity()){
            DataPoint dataPoint = new DataPoint();
            dataPoint.setLabel((((Object[]) object)[0]).toString());
            dataPoint.setY(Float.valueOf((((Object[]) object)[1]).toString()));
            list.add(dataPoint);
        }
        return list;
    }

    @Override
    public List<DataPoint> statNumberSadReactsByUniversity() {
        List<DataPoint> list = new ArrayList<DataPoint>();
        for(Object object:reactRepository.numberSadReactByUniversity()){
            DataPoint dataPoint = new DataPoint();
            dataPoint.setLabel((((Object[]) object)[0]).toString());
            dataPoint.setY(Float.valueOf((((Object[]) object)[1]).toString()));
            list.add(dataPoint);
        }
        return list;
    }


}
