package com.javachinna.service;

import com.javachinna.model.Comment;
import com.javachinna.model.Topic;
import com.javachinna.model.User;
import com.javachinna.repo.ICommentRepo;
import com.javachinna.repo.ITopicRepo;
import com.javachinna.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class CommentService implements ICommentService{

    @Autowired
    ITopicRepo iTopicRepo;
    @Autowired
    ICommentRepo iCommentRepo;
    @Autowired
    UserRepository userRepository;


    @Override
    public void AddAffectCommentList(Comment comment, Long idTopic, Long idUser) {


        Topic topic =  iTopicRepo.findById(idTopic).orElse(null);
        User user =  userRepository.findById(idUser).orElse(null);

            comment.setTopic(topic);
            comment.setUser(user);
            iCommentRepo.save(comment);

    }


    @Override
    public Comment upDateComment(Comment comment, Long idTopic, Long idUser) {

        Topic topic =  iTopicRepo.findById(idTopic).orElse(null);
        User user =  userRepository.findById(idUser).orElse(null);


        comment.setTopic(topic);
        comment.setUser(user);


        return iCommentRepo.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return (List<Comment>) iCommentRepo.findAll();
    }


    @Override
    public void deleteComment(Integer idComment) {

        log.info("In methode deleteComment");
        log.warn("Are you sure you want to delete Comment");

        iCommentRepo.deleteById(idComment);
        log.error("exeption");

    }


    @Override
    public List<Comment> getAllCommentsByTopic(Long idTopic) {

        Topic topic = iTopicRepo.findById(idTopic).orElse(null);
        return iTopicRepo.getAllCommentsByTopic(topic);

    }


    @Override
    public void likeComment(Integer idComment) {
        Comment comment = iCommentRepo.findById(idComment).orElse(null);

        comment.setLikeComment(comment.getLikeComment()+1);
        iCommentRepo.save(comment);


    }

    @Override
    public void dislikeComment(Integer idComment) {
        Comment comment = iCommentRepo.findById(idComment).orElse(null);

        comment.setDislikeComment(comment.getDislikeComment()+1);
        iCommentRepo.save(comment);

    }

}
