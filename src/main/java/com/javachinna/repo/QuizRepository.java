package com.javachinna.repo;

import com.javachinna.model.Question;

import com.javachinna.model.Quiz;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

    @Query(value = "select  a from Quiz q join q.questions a where q.quizId=:id")
    List<Question> getQuizQuestion(@Param("id") Long idQ);
	
}
