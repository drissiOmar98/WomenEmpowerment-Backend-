package com.javachinna.dto;


public class AnswersDTO {

	String[] answers = new String[20]; 
	
	public AnswersDTO() {
		
	}
	

	public AnswersDTO(String[] answers) {
		super();
		this.answers = answers;
	}


	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	
	
}
