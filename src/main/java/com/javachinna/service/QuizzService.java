package com.javachinna.service;


import com.javachinna.model.*;
import com.javachinna.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
@Slf4j
public class QuizzService implements IQuizServiceC{

    @Autowired
    private UserRepository repoUser;
    @Autowired
    private RepoCandidacy repoCandidacy;
    @Autowired
    private RepoQuestionCandidacy repoQuestionCandidacy;
    @Autowired
    private RepoQuiz repoQuiz;
    @Autowired
    private RepoResultQuiz repoResultQuiz;



    @Override
    public void addQuiz(QuizCandidacy quiz, Integer id) {

        Candidacy candidacy = this.repoCandidacy.findById(id).orElse(null);
        quiz.setCandidacy(candidacy);
        quiz.setCreateAt(new Date());
        repoQuiz.save(quiz);

    }

    @Override
    public void addQuestionAndAsigntoQuiz(QuestionCandidacy question, Integer idQuiz) {
        QuizCandidacy quiz = repoQuiz.findById(idQuiz).orElse(null);
        question.setQuiz(quiz);
        repoQuestionCandidacy.save(question);
    }

    @Override
    public List<QuestionCandidacy> getQuizQuestion(Integer idQuiz) {

        List<QuestionCandidacy> allQues = (List<QuestionCandidacy>) repoQuestionCandidacy.findAll();
        List<QuestionCandidacy> qList = new ArrayList<>();

        Random random = new Random();

        for(int i=0; i<5; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        return qList;
    }

    @Override
    public List<QuestionCandidacy> getQuestions() {
        List<QuestionCandidacy> allQues = (List<QuestionCandidacy>) repoQuestionCandidacy.findAll();
        List<QuestionCandidacy> qList = new ArrayList<QuestionCandidacy>();

        Random random = new Random();

        for(int i=0; i<5; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        return qList;
    }


    public int getResult(QuestionCandidacy questionCandidacy) {
        int correct = 0;

        for (QuestionCandidacy q : questionCandidacy.getQuiz().getQuestion())

            if (q.getAns() == q.getChose())
                correct++;

            return correct;

        }


   @Override
    public Integer SaveScore(ResultQuiz resultQuiz, Long idUser, Integer idQuiz) {
        ResultQuiz saveResult = new ResultQuiz();

        User user = this.repoUser.findById(idUser).orElse(null);
        QuizCandidacy quiz = this.repoQuiz.findById(idQuiz).orElse(null);

        if (repoResultQuiz.findUser(idUser,idQuiz) == 0)
        {
            saveResult.setSUser(user);
            saveResult.setQuiz(quiz);
            saveResult.setUsername(resultQuiz.getUsername());
            saveResult.setTotalCorrect(resultQuiz.getTotalCorrect());
            saveResult.setCorrectAnswer(resultQuiz.getCorrectAnswer());
            saveResult.setInCorrectAnswer(resultQuiz.getInCorrectAnswer());
            repoResultQuiz.save(saveResult);
            return 1;

        }else{
            log.info("This user is tested with this quiz");
            return 0;
        }

    }



    @Override
    public void DeleteQuiz(Integer idQ) {
        this.repoQuiz.deleteById(idQ);

    }

/*
    @Override
    public Integer MaxScoreInCandidacy() {
        return null;
                //this.repoUser.MaxScoreInCandidacy();
    }

    @Override
    public List<Object> getCandidacyWithScoreQuiz(Integer id) {
        return null;
    }

    @Override
    public List<Object> getCandidacytWithScoreQuiz(Integer id) {

        return this.repoUser.getCandidacytWithScoreQuiz(id);
    }

    @Override
    public List<ResultQuiz> getTopScore() {
        List<ResultQuiz> sList = (List<ResultQuiz>) repoResultQuiz.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));

        return sList;
    }

    @Override
    public Integer getScore(Long idU) {
        return repoResultQuiz.getScore(idU);
    }

    @Override
    public List<QuizCandidacy> getQuizByCandidacy(Integer id) {
        return this.repoQuiz.getQuizByCandidacy(id);
    }



    @Override
    public Integer getScoreByUser(Integer idUser) {
        return null;
    }



 */

}
