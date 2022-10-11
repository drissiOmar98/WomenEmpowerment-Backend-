package com.javachinna.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {


	private static final long serialVersionUID = 65981149772133526L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "PROVIDER_USER_ID")
	private String providerUserId;

	private String firstName;
	private String lastName;

	@Column(name = "enabled", columnDefinition = "BIT", length = 1)
	private boolean enabled;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "created_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifiedDate;

	private String password;

	private String provider;

	@Enumerated(EnumType.STRING)
	private Profession profession;

	private Integer priceconsultation;

	private Integer Score;

	//@Positive
	private Integer salary;

	//@Positive
	private Integer tarifHoraire;

//	@NotNull
//	@Email(message = "Email should be valid")
	private String email;

	//@Min(value = 18, message = "Age should not be less than 18")
	//@Max(value = 150, message = "Age should not be greater than 150")
	private Integer age;


	@JsonIgnore
	private int banned;
	@Enumerated(EnumType.STRING)
	private Nationality nationality;


	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private State state;


	// bi-directional many-to-many association to Role


	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	@JsonIgnore
	private Set<Role> roles;


	@ManyToMany(mappedBy = "users",cascade =CascadeType.ALL)
	@JsonIgnore
	private Set<Subscription> subscs;


	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy ="User")
	@JsonIgnore
	private Session session;

	//private Integer nb_subsc;
	@OneToMany
	@JsonIgnore
	private Set<Appointment> appointments;

	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Complaint> complaints;
	@OneToMany(mappedBy ="users",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<ComplaintResponse> complaintResponses;
	@OneToMany(cascade =  CascadeType.ALL, mappedBy = "doctor")
	@JsonIgnore
	private Set<Appointment> appointmentsDocteur;

	@ManyToMany (mappedBy = "doctors",cascade =CascadeType.ALL)
	@JsonIgnore
	private Set<Clinical> clinical;

	@OneToMany(mappedBy ="users" ,fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REMOVE
			})
	@JsonIgnore
	private Set<Offres> offer;


	@OneToMany(mappedBy ="usersW",fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REMOVE
			})
	@JsonIgnore
	private  Set<Candidacy> candidacy;



	@OneToMany(mappedBy = "formateur")
	@JsonIgnore
	private Set<Formation> formationF;


	@ManyToMany(mappedBy = "apprenant", fetch = FetchType.LAZY
			,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	@JsonIgnore
	private Set<Formation> formationA;


	@OneToMany(mappedBy = "sUser"
			,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	@JsonIgnore
	private Set<Result> results;

	@OneToMany(mappedBy = "userC",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<PostComments> postComments;


	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Certificat> certificats;

	/*

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Likes> likes;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Dislikes> dislikes;

	 */

	@OneToMany(mappedBy = "user",cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.MERGE},
			fetch=FetchType.LAZY)
	@JsonIgnore
	private Set<PartnerInstitution> partnerInstitutions ;



	@OneToMany(mappedBy = "user",cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.MERGE},
			fetch=FetchType.LAZY)
	@JsonIgnore
	private Set<CandidacyUniversity> candidacies;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<DatabaseFile> fileUploads;

	/////Nesrine///
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<Comment> comments;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<Topic> topics;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<Publicity> publicities;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<ReactComment> reactComments;

	@OneToMany(mappedBy = "user",cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.MERGE},
			fetch=FetchType.LAZY)
	@JsonIgnore
	private Set<CommentUniversity> commentUniversities;

	@OneToMany(mappedBy = "user",cascade={CascadeType.PERSIST, CascadeType.REMOVE},
			fetch=FetchType.EAGER)
	@JsonIgnore
	private Set<Rating>ratings;

	@OneToMany(mappedBy = "user",cascade={CascadeType.PERSIST, CascadeType.REMOVE},
			fetch=FetchType.EAGER)
	@JsonIgnore
	private Set<React> reacts;

}