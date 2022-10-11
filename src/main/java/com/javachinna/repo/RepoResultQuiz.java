package com.javachinna.repo;

import com.javachinna.model.ResultQuiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RepoResultQuiz extends CrudRepository<ResultQuiz, Integer> {




    @Query(value = "select count(r.id) from ResultQuiz r join r.sUser u join r.quiz q where u.id=:idu and q.idQuiz=:idq and r.status=false")
    Integer findUser(@Param("idu") Long idUser, @Param("idq") Integer idQuiz);





/*

    @Query(value = "select count(r.id) from ResultQuiz r join r.sUser u where u.idUser=:idu")
    Integer getNbrQuiz(@Param("idu") Long idU);

    @Query(value = "select sum(r.totalCorrect) from ResultQuiz r join r.sUser u where u.idUser=:idu group by r.sUser ")
    Integer getScore(@Param("idu") Long idU);*/

}
