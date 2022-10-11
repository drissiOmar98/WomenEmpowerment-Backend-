package com.javachinna.controller;

import com.javachinna.dto.AnswersDTO;
import com.javachinna.model.*;
import com.javachinna.service.QuestionService;
import com.javachinna.service.QuizService;
import com.javachinna.service.ServiceQuiz;
import com.javachinna.service.SessionService;
import com.javachinna.utils.Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@RestController
@RequestMapping("/quiz")
public class QuizController {

	private QuestionService questionService;

	@Autowired
	private QuizService iServicesQuiz;


	public QuizController(QuestionService questionService) {
		super();
		this.questionService = questionService;
	}


//	@PostMapping("/addQuiz")
//	@ApiOperation(value = " add Quiz ")
//	public void addQuiz(@RequestBody Quiz quiz)
//	{
//		iServicesQuiz.addQuiz(quiz);
//	}

	@PostMapping("/addQuestionAndAsigntoQuiz/{idQuiz}")
	@ApiOperation(value = " add Question And Asign To Quiz ")
	public void addQuestionAndAsigntoQuiz(@RequestBody Question question, @PathVariable(name = "idQuiz")  Long idQuiz)
	{
		iServicesQuiz.addQuestionAndAsigntoQuiz(question, idQuiz);
	}




	@ApiOperation(value = "get Quiz Question")
	@GetMapping("/getQuizQuestion/{id}")
	public List<Question> getQuizQuestion(@PathVariable("id") Long idQ)
	{
		return iServicesQuiz.getQuizQuestion(idQ);
	}



	@ApiOperation(value = "get Quiz Questions")
	@GetMapping("/getQuizQuestions")
	public List<Question> getQuestions()
	{

		return iServicesQuiz.getQuestions();
	}


	@PostMapping("/SaveScore/{idU}/{idQ}")
	@ApiOperation(value = " Save Score Quiz ")
	public Integer saveScore(@RequestBody Result result,@PathVariable(name = "idU") Long idUser,@PathVariable(name = "idQ") Long idQuiz)
	{
		return   this.iServicesQuiz.saveScore(result,idUser,idQuiz);
	}
	@ApiOperation(value = "Delete Quiz")
	@GetMapping("/DeleteQuiz/{id}")
	@ResponseBody
	public void DeleteQuiz(@PathVariable("id") Long idQ)
	{
		this.iServicesQuiz.DeleteQuiz(idQ);
	}
	@ApiOperation(value = " get Score By User  ")
    @GetMapping("/getScore/{id}")
    @ResponseBody
    public Integer getScore(@PathVariable("id") Long idU)
    {
        return iServicesQuiz.getScoreByUser(idU);
    }



}
