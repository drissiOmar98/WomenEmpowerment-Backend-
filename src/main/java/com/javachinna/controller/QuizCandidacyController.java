package com.javachinna.controller;


import com.javachinna.model.QuestionCandidacy;
import com.javachinna.model.QuizCandidacy;
import com.javachinna.model.ResultQuiz;
import com.javachinna.service.QuizzService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/quizCandidacy")
public class QuizCandidacyController {

	@Autowired
	private QuizzService iQuizServiceC;



	public QuizCandidacyController(@RequestBody QuizzService serviceQuizz) {
		super();
		this.iQuizServiceC = serviceQuizz;
	}


    @PostMapping("/addQuiz/{idF}")
    @ApiOperation(value = " add Quiz ")
    public void addQuiz(@RequestBody QuizCandidacy quiz, @PathVariable(name = "idF") Integer idF)
    {
        iQuizServiceC.addQuiz(quiz,idF);
    }

	@PostMapping("/addQuestionAndAsigntoQuiz/{idQuiz}")
	@ApiOperation(value = " add Question And Asign To Quiz ")
	public void addQuestionAndAsigntoQuiz(@RequestBody QuestionCandidacy question, @PathVariable(name = "idQuiz") Integer idQuiz)
	{
		iQuizServiceC.addQuestionAndAsigntoQuiz(question, idQuiz);
	}




	@ApiOperation(value = "get Quiz Question")
	@GetMapping("/getQuizQuestion/{id}")
	public List<QuestionCandidacy> getQuizQuestion(@PathVariable("id") Integer idQuiz)
	{
		return iQuizServiceC.getQuizQuestion(idQuiz);
	}



	@ApiOperation(value = "get Quiz Questions")
	@GetMapping("/getQuizQuestions")
	public List<QuestionCandidacy> getQuestions()
	{
		return iQuizServiceC.getQuestions();
	}


	@PostMapping("/SaveScore/{idUser}/{idQuiz}")
	@ApiOperation(value = " Save Score Quiz ")
	public Integer saveScore(@RequestBody ResultQuiz result, @PathVariable(name = "idUser") Long idUser, @PathVariable(name = "idQuiz") Integer idQuiz)
	{
		return  this.iQuizServiceC.SaveScore(result,idUser,idQuiz);
	}


	@ApiOperation(value = "Delete Quiz")
	@GetMapping("/DeleteQuiz/{id}")
	@ResponseBody
	public void DeleteQuiz(@PathVariable("id") Integer idQuiz)
	{
		this.iQuizServiceC.DeleteQuiz(idQuiz);
	}
	/*
	@ApiOperation(value = " get Score By User  ")
    @GetMapping("/getScore/{id}")
    @ResponseBody
    public Integer getScore(@PathVariable("id") Integer idUser)
    {
        return iQuizServiceC.getScoreByUser(idUser);
    }


	 */


}
