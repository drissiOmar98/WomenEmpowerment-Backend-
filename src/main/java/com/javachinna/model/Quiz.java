package com.javachinna.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter

@Entity
@Table(name = "Quizes")
public class Quiz {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long quizId;
	

	
	@OneToMany(targetEntity=Question.class, mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Question> questions;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "quiz")
	private Session session;
	
	
	public Quiz() {

	}

	public Quiz( List<Question> questions, Session session) {
		super();

		this.questions = questions;
		this.session = session;
	}


	
	
}
