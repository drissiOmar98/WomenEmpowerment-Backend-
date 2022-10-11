package com.javachinna.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.javachinna.dto.LocalUser;
import com.javachinna.dto.SignUpRequest;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.User;


public interface UserService {


	User addUser (User user);

	void updateUser(User u, Long idU);

	User retrieveUser (Long id);

	List<User> retrieveAllUsers();

	void deleteUser ( Long id);

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	User excluded (Long id) ;
	User warned (Long id);
	User punished (Long id) ;


	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
	//OMAR
	public int numStudentWithoutRatings();
	public int numStudentsWithRatingsByUniversity(Integer idUniversity);
	public List<User>acceptedStudentsByUniversity( Integer idUniversity);
	public int studentDemands(Long IdStudent,Integer IdUniversity);
	public Map<String, Double> PercentageStudentsByNationality() ;
}
