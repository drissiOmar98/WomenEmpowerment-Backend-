package com.javachinna.repo;

import com.javachinna.model.PostComments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICommentsRepo extends CrudRepository<PostComments,Integer> {

    @Query(value = "select c from PostComments c join c.userC u where u.id =:id and c.message='This message was blocked'")
    List<PostComments> listeCommentParUser(@Param("id") Long idUser);
}
