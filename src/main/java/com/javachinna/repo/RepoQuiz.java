package com.javachinna.repo;



import com.javachinna.model.QuestionCandidacy;
import com.javachinna.model.QuizCandidacy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface RepoQuiz extends CrudRepository<QuizCandidacy , Integer> {

    @Query(value = "select  C from QuizCandidacy C join C.question a where C.idQuiz=:id")
    List<QuestionCandidacy> getQuizQuestion(@Param("id") Integer idQuiz);


  /*  @Query(value = "select  K from QuizCandidacy K join K.candidacy F where k.idQuiz=:id")
    List<QuizCandidacy> getQuizByCandidacy(@Param("id") Integer idQuiz);*/



}
