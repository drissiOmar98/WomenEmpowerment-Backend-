package com.javachinna.service;

import com.javachinna.model.Comment;

import java.util.List;

public interface ICommentService {

    void AddAffectCommentList(Comment comment, Long idTopic, Long idUser);

     Comment upDateComment (Comment comment, Long idTopic, Long idUser);

     List<Comment> getAllComments();

    void deleteComment(Integer idComment);

     List<Comment> getAllCommentsByTopic(Long idTopic);


     void likeComment(Integer idComment);
    void dislikeComment(Integer idComment);

    //////////////////////REACT////////////////////
/*
    List<ReactComment> findAllByIdComment(Long idReact);

    List<ReactComment> findAllByIdCommentAndEmoji(Long idReact, TypeRating typeRating);

    Long  countAllByIdComment(Long idReact) ;


 */
/////////////////////////////
}
