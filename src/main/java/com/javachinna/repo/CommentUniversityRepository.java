package com.javachinna.repo;


import com.javachinna.model.CommentUniversity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentUniversityRepository extends JpaRepository<CommentUniversity, Long > {
        @Query("select c from CommentUniversity c where c.partnerInstitution.idPartner=:id")
        List<CommentUniversity> findByUniversityId(@Param("id") Integer id);
     //   @Query("select  c from Comment  c join c.estimations e where c.id = e   ")
//        @Query(value = "SELECT c.* FROM comment  c , estimation e WHERE e.comment_id = c.id " +
//                "And count(e) >= (SELECT MAX (count(e))FROM comment c ,estimation e WHERE c.id = e.id ) " ,
//                nativeQuery = true)
//        List<Comment> commentsplusper();


}
