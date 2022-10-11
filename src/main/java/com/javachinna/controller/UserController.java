package com.javachinna.controller;


import com.javachinna.dto.QuestionsDTO;

import com.javachinna.model.*;
import com.javachinna.repo.OptionRepository;
import com.javachinna.service.QuestionService;
import com.javachinna.service.UserService;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.javachinna.config.CurrentUser;
import com.javachinna.dto.LocalUser;
import com.javachinna.util.GeneralUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private QuestionService questionService;
	@Autowired
	private OptionRepository optionRepository ;

	@ApiOperation(value = "retrieve All Users ")
	@GetMapping("/retrieve-All-Users")
	@ResponseBody
	public List<User> RetrieveAllUsers()
	{
		return  userService.retrieveAllUsers();
	}




	@ApiOperation(value = "update User By Id ")
	@PutMapping("/updateUserById/{idU}")
	@ResponseBody
	public void UpdateUser(@RequestBody User u, @PathVariable(name="idU") Long idUser)
	{
		userService.updateUser(u,idUser);
	}


	@ApiOperation(value = "delete User By Id ")
	@DeleteMapping("/deleteUserById/{idCom}")
	@ResponseBody
	public void DeleteUser(@PathVariable("idU") Long idU)
	{
		userService.deleteUser(idU);

	}


	@ApiOperation(value = "Retrieve User by ID ")
	@GetMapping("/retrieve-User-by-ID/{idU}")
	@ResponseBody
	public User RetrieveUser(@PathVariable("idU") Long idU)
	{
		return userService.retrieveUser(idU);
	}






	@GetMapping("/GetUser/{id}")
	@ResponseBody
	@ApiOperation(value = "Get User ")
	public Optional<User> GetUser(@PathVariable ("id") Long idUser) {
		return userService.findUserById(idUser);
	}



	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
		return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getContent() {
		return ResponseEntity.ok("Public content goes here");
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserContent() {
		return ResponseEntity.ok("User content goes here");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAdminContent() {
		return ResponseEntity.ok("Admin content goes here");
	}



	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<?> getModeratorContent() {
		return ResponseEntity.ok("Moderator content goes here");
	}


	@PostMapping("/moderateur/question/add")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	public String addQuestion(@RequestBody QuestionsDTO questionsDto, Model model) {

		Question question = new Question();
		List<Option> options = new ArrayList<>();
		Option optionOne = new Option(questionsDto.getOptionOne());
		Option optionTwo = new Option(questionsDto.getOptionTwo());
		Option optionThree = new Option(questionsDto.getOptionThree());
		Option optionFour = new Option(questionsDto.getOptionFour());
		options.add(optionOne);
		options.add(optionTwo);
		options.add(optionThree);
		options.add(optionFour);
		question.setTitle(questionsDto.getQuestionTitle());


		switch (questionsDto.getCorrectAnswer()) {
			case "1":
				question.setOptionCorrect(questionsDto.getOptionOne());
				break;
			case "2":
				question.setOptionCorrect(questionsDto.getOptionTwo());
				break;
			case "3":
				question.setOptionCorrect(questionsDto.getOptionThree());
				break;
			case "4":
				question.setOptionCorrect(questionsDto.getOptionFour());
				break;
		}

		for(Option option: options) {
			option.setQuestion(question);
		}
		question.setOptions(options);
		questionService.saveQuestion(question);
		return "redirect:/teacher/questions/list";
	}

	@GetMapping("/moderator/question/{id}")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	public String showModifyQuestionForm(@PathVariable Long id, Model model) {
		Question existingQuestion = questionService.findQuestionByquestionId(id);
		QuestionsDTO questionsDto = new QuestionsDTO();
		questionsDto.setQuestionTitle(existingQuestion.getTitle());
		List<Option> options = existingQuestion.getOptions();
		questionsDto.setOptionOne(options.get(0).getOptionText());
		questionsDto.setOptionTwo(options.get(1).getOptionText());
		questionsDto.setOptionThree(options.get(2).getOptionText());
		questionsDto.setOptionFour(options.get(3).getOptionText());
		questionsDto.setCorrectAnswer(existingQuestion.getOptionCorrect());

		questionsDto.setQuestionId(existingQuestion.getQuestionId());
		model.addAttribute("questionsDto", questionsDto);
		return "teacher-questions-edit";
	}

	@PostMapping("/moderator/question/{id}")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	public String updateQuestionDetails(@PathVariable Long id, @RequestBody QuestionsDTO questionsDto, Model model) {

		Question existingQuestion = questionService.findQuestionByquestionId(id);
		List<Option> existingOptions = existingQuestion.getOptions();
		existingQuestion.setTitle(questionsDto.getQuestionTitle());

		existingOptions.get(0).setOptionText(questionsDto.getOptionOne());
		existingOptions.get(1).setOptionText(questionsDto.getOptionTwo());
		existingOptions.get(2).setOptionText(questionsDto.getOptionThree());
		existingOptions.get(3).setOptionText(questionsDto.getOptionFour());
		switch (questionsDto.getCorrectAnswer()) {
			case "1":
				existingQuestion.setOptionCorrect(questionsDto.getOptionOne());
				break;
			case "2":
				existingQuestion.setOptionCorrect(questionsDto.getOptionTwo());
				break;
			case "3":
				existingQuestion.setOptionCorrect(questionsDto.getOptionThree());
				break;
			case "4":
				existingQuestion.setOptionCorrect(questionsDto.getOptionFour());
				break;
		}

		questionService.saveQuestion(existingQuestion);
		return "redirect:/teacher/questions/list";

	}

	@GetMapping("/moderator/questions/list")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	@JsonIgnore
	public List<Question> showAllQuestionsPage() {
		List<Question> Questions = questionService.getAllQuestions();




		return Questions;
	}


	@GetMapping("/moderator/question/delete/{id}")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	public String deleteQuestion(@PathVariable Long id, Model model) {
		Question question = questionService.findQuestionByquestionId(id);
		List<Option> options = question.getOptions();
		for(Option option: options) {
			optionRepository.delete(option);
		}
		questionService.deleteQuestionByquestionId(id);
		return "redirect:/teacher/questions/list";
	}

}