package com.javachinna.repo;


import com.javachinna.model.QuestionCourses;
import com.javachinna.model.QuizCourses;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuizRepo extends CrudRepository<QuizCourses,Integer> {

    @Query(value = "select  a from QuizCourses q join q.question a where q.idQuiz=:id")
    List<QuestionCourses> getQuizQuestion(@Param("id") Integer idQ);


    @Query(value = "select  q from QuizCourses q join q.formation f where f.idFormation=:id")
    List<QuizCourses> getQuizByCourses(@Param("id") Integer idF);


}
