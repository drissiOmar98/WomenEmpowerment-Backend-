package com.javachinna.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="sessions")
public class Session {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "User_id")
	private User User;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "session_id")
	private Quiz quiz;
	
	@Column(name = "start_time")
	private Timestamp start_time;
	
	@Column(name = "end_time")
    private Timestamp end_time;
	
	@Column(name = "score")
	private double score;

	@Column(name = "msg")
	private String msg;
	
	@Column(name="current_User_id")
	private Long UserId;



	private String UserName;
	
}
