package com.javachinna.service;

import com.javachinna.model.Question;
import com.javachinna.repo.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
	

	private QuestionRepository questionRepository;

	public QuestionServiceImpl(QuestionRepository questionRepository) {
		super();
		this.questionRepository = questionRepository;
	}

	@Override
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	@Override
	public Question saveQuestion(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public Question findQuestionByquestionId(Long id) {
		return questionRepository.findByquestionId(id);
	}

	@Override
	public void deleteQuestionByquestionId(Long id) {
		questionRepository.deleteById(id);
		return;
	}
	
}
