package com.javachinna.service;

import com.javachinna.model.*;
import com.javachinna.repo.IResultRepo;
import com.javachinna.repo.QuestionRepository;
import com.javachinna.repo.QuizRepository;
import com.javachinna.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Slf4j
@Service
public class QuizServiceImpl implements QuizService{
	
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private QuestionRepository questRepository;
	@Autowired
	private IResultRepo iResultRepo;
	@Autowired
	private UserRepository iUserRepo;
	
	
	public QuizServiceImpl(QuizRepository quizRepository) {
		super();
		this.quizRepository = quizRepository;
	}


	public Quiz saveQuiz(Quiz quiz) {
		return quizRepository.save(quiz);
	}

	@Override
	public void addQuestionAndAsigntoQuiz(Question question, Long idQuiz) {
		Quiz quiz = quizRepository.findById(idQuiz).orElse(null);

		question.setQuiz(quiz);

		questRepository.save(question);
	}


	@Override
	public List<Question> getQuizQuestion(Long idQuiz) {
		List<Question> allQues =  quizRepository.getQuizQuestion(idQuiz);
		List<Question> qList = new ArrayList<>();

		Random random = new Random();

		for(int i=0; i<5; i++) {
			int rand = random.nextInt(allQues.size());
			qList.add(allQues.get(rand));
			allQues.remove(rand);
		}

		return qList;

	}

	@Override
	public List<Question> getQuestions() {
		List<Question> allQues = (List<Question>) questRepository.findAll();
		List<Question> qList = new ArrayList<Question>();

		Random random = new Random();

		for(int i=0; i<5; i++) {
			int rand = random.nextInt(allQues.size());
			qList.add(allQues.get(rand));
			allQues.remove(rand);
		}

		return qList;
	}



	@Override
	public Integer saveScore(Result result, Long idUser, Long idQuiz) {
		Result saveResult = new Result();

		User user = this.iUserRepo.findById(idUser).orElse(null);
		Quiz quizz = this.quizRepository.findById(idQuiz).orElse(null);
		if (iResultRepo.findUserQuiz(idUser, Math.toIntExact(idQuiz)) == 0)
		{
			saveResult.setSUser(user);
			// saveResult.setQuizz(quizz);

			saveResult.setUsername(result.getUsername());
			saveResult.setTotalCorrect(result.getTotalCorrect());
			saveResult.setCorrectAnswer(result.getCorrectAnswer());
			saveResult.setInCorrectAnswer(result.getInCorrectAnswer());
			iResultRepo.save(saveResult);
			return 1;

		}else{
			log.info("This user is tested with this quiz");
			return 0;
		}

	}

//	@Override
//	public List<Object> getUserWithScoreQuiz(Long id) {
//		List<Result> sList = (List<Result>) iResultRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
//
//		return sList;
//	}

	@Override
	public Integer getScoreByUser(Long idU) {
		return iResultRepo.getScore(idU);
	}

	@Override
	public void DeleteQuiz(Long idQ) {
		quizRepository.deleteById(idQ);

	}


}
