package com.javachinna.repo;

import com.javachinna.model.RatingPartner;
import com.javachinna.model.ReactComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReactRepo extends CrudRepository<ReactComment,Integer> {


    @Query("select r from ReactComment r where r.user.id=:idUser and r.comment.idComment=:idComment")
    List<ReactComment> getReactByCommentAndUser(@Param("idComment") Integer idComment, @Param("idUser") Long idUser);
}
