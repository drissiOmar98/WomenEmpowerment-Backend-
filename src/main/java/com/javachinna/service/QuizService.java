package com.javachinna.service;

import com.javachinna.model.*;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuizService {

	void addQuestionAndAsigntoQuiz(Question question, Long idQuiz);


    List<Question> getQuizQuestion(Long idQuiz);

    List<Question> getQuestions();


	Integer saveScore(Result result, Long idUser, Long idQuiz);

	//List<Object> getUserWithScoreQuiz(@Param("id")  Long id);

	Integer getScoreByUser( Long idU);

	void DeleteQuiz(Long idQ);
	//void UpdateQuiz(Long idQ);





	
}
