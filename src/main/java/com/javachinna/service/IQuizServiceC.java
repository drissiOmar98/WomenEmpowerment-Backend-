package com.javachinna.service;



import com.javachinna.model.QuestionCandidacy;
import com.javachinna.model.QuizCandidacy;
import com.javachinna.model.ResultQuiz;
import org.springframework.data.repository.query.Param;

import java.util.List;

//import com.javachinna.model.*;


public interface IQuizServiceC {


    void addQuiz(QuizCandidacy quiz, Integer idF);
    void addQuestionAndAsigntoQuiz(QuestionCandidacy question, Integer idQuiz);
   // void addAnswerAndAsigntoQuestion(Answer answer,Integer idQuestion,Integer idQuiz);

    List<QuestionCandidacy> getQuizQuestion(Integer idQuiz);
    List<QuestionCandidacy> getQuestions();
    //int getResult(QuestionCandidacy questionCandidacy);

    Integer SaveScore (ResultQuiz result, Long idUser, Integer idQuiz);
    void DeleteQuiz(Integer idQuiz);
  /*
    Integer MaxScoreInCandidacy();

    List<Object> getCandidacyWithScoreQuiz(@Param("id") Integer id);

    List<Object> getCandidacytWithScoreQuiz(Integer id);

    List<ResultQuiz> getTopScore();

    Integer getScore(Long idU);

    List<QuizCandidacy> getQuizByCandidacy(Integer idF);




    Integer getScoreByUser(Integer idUser);


 */
}