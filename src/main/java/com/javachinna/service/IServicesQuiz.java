package com.javachinna.service;


import com.javachinna.model.*;
import org.springframework.data.repository.query.Param;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface IServicesQuiz {


    void addQuiz(QuizCourses quiz, Integer idF);
    void addQuestionAndAsigntoQuiz(QuestionCourses question, Integer idQuiz);
   // void addAnswerAndAsigntoQuestion(Answer answer,Integer idQuestion,Integer idQuiz);

    List<QuestionCourses> getQuizQuestion(Integer idQuiz);
    List<QuestionCourses> getQuestions();
    int getResult(QuestionForm qForm);

    Integer saveScore(Result result, Long idUser, Integer idQuiz);

    User ApprenentwithMaxScoreInFormation(Integer id);

    List<Object> ApprenentwithMaxScore(Integer id);

    Integer MaxScoreInFormation();

    List<User> getApprenantWithScoreQuiz( Integer id);
    User ApprenentwithMaxScoreQuiz(Integer id);

    List<Result> getTopScore();

    Integer getScore( Long idU);

    List<QuizCourses> getQuizByFormation(Integer idF);

    void DeleteQuiz(Integer idQ);




    void giftsToUserMaxScoreInCourses();


    void ResultQuiz() throws IOException, MessagingException;





}
