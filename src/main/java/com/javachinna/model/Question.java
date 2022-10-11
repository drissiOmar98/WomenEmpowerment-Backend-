package com.javachinna.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Questions")
public class Question{

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long questionId;
	
	@Column(name = "question_title", nullable = false)
	private String title;
	
	@OneToMany(targetEntity=Option.class, mappedBy="question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Option> options; //value to text
	
	
	@Column(name = "option_correct", nullable = false)
	private String optionCorrect;
	
	@ManyToOne()
	@JoinColumn(name="quiz_id")
	private Quiz quiz;
	

	
	private String optionChosen =null;


	
	
}
