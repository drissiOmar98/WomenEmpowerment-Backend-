package com.javachinna.service;

import com.javachinna.model.ReactComment;
import com.javachinna.model.TypeRating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReactCommentService {

   public void addReact (ReactComment reactComment);
    public void addReactAndAffectToUserAndComment(TypeRating typeRating, Long idUser, Integer idComment);
    List<ReactComment> getReactByCommentAndUser(Integer idComment , Long idUser  );

}
