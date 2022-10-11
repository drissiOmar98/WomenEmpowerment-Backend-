package com.javachinna.service;

import java.util.*;

import com.javachinna.model.*;
import com.javachinna.repo.ICandidacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.javachinna.dto.LocalUser;
import com.javachinna.dto.SignUpRequest;
import com.javachinna.dto.SocialProvider;
import com.javachinna.exception.OAuth2AuthenticationProcessingException;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.repo.RoleRepository;
import com.javachinna.repo.UserRepository;
import com.javachinna.security.oauth2.user.OAuth2UserInfo;
import com.javachinna.security.oauth2.user.OAuth2UserInfoFactory;
import com.javachinna.util.GeneralUtils;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	ICandidacyRepository candidacyRepository;
	@Override
	public User addUser(User user) {

		return userRepository.save(user);
	}

	@Override
	public void updateUser(User u, Long idU) {
		User user=userRepository.findById(idU).orElse(null);
		userRepository.save(user);
	}

	@Override
	public User retrieveUser(Long id) {
		User u = userRepository.findById(id).orElse(null);
		return u;

	}

	@Override
	public List<User> retrieveAllUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(user -> {
			users.add(user);
		});
		return users;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);

	}

	@Override
	@Transactional(value = "transactionManager")
	public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}

		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setCreatedDate(now);
		user.setModifiedDate(now);
		user.setAge(signUpRequest.getAge());
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setSalary(signUpRequest.getSalary());
		user.setTarifHoraire(signUpRequest.getTarifHoraire());
		user.setPriceconsultation(signUpRequest.getPriceconsultation());
		user.setProfession(signUpRequest.getProfession());
		user.setScore(signUpRequest.getScore());
		user.setNationality(signUpRequest.getNationality());
		user.setPhoneNumber(signUpRequest.getPhoneNumber());
		user = userRepository.save(user);
		userRepository.flush();
		return user;

	}

	private User buildUser(final SignUpRequest formDTO) {
		User user = new User();
		user.setDisplayName(formDTO.getDisplayName());
		user.setEmail(formDTO.getEmail());
		user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		user.setRoles(roles);
		user.setFirstName(formDTO.getFirstName());
		user.setAge(formDTO.getAge());
		user.setLastName(formDTO.getLastName());
		user.setSalary(formDTO.getSalary());
		user.setTarifHoraire(formDTO.getTarifHoraire());
		user.setPriceconsultation(formDTO.getPriceconsultation());
		user.setProfession(formDTO.getProfession());
		user.setScore(formDTO.getScore());
		user.setProvider(formDTO.getSocialProvider().getProviderType());
		user.setState(formDTO.getState());
		user.setEnabled(true);
		user.setProviderUserId(formDTO.getProviderUserId());
		return user;
	}

	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		User user = findUserByEmail(oAuth2UserInfo.getEmail());
		if (user != null) {
			if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(userDetails);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}
////////////ahaya lezem tetbadel ////////////////////////////////  voir ici pls

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User excluded(Long id) {
		User user =  userRepository.findById(id).get();
		user.setState(State.EXCLUDED);
		return userRepository.save(user);
	}

	@Override
	public User warned(Long id) {
		User user =  userRepository.findById(id).get();
		user.setState(State.WARNED);
		return userRepository.save(user);
	}

	@Override
	public User punished(Long id) {
		User user =  userRepository.findById(id).get();
		user.setState(State.PUNISHED);
		return userRepository.save(user);
	}


	//OMAR
	@Override
	public int  numStudentWithoutRatings() {
		List<User>listStudent= (List<User>) userRepository.findStudentWithoutRatings();
		int num=0;
		for(User u :listStudent){
			{
				num=num+1;
			}
		}
		return num;
	}

	@Override
	public int numStudentsWithRatingsByUniversity(Integer idUniversity) {
		List<User> listS=userRepository.findStudentWithRatingByUniversity( idUniversity);
		int num=0;
		for (User u : listS){
			num+=1;
		}

		return  num;

	}


	@Override
	public List<User> acceptedStudentsByUniversity(Integer idUniversity) {
		return userRepository.acceptedStudentsByUniversity(idUniversity);
	}

	@Override
	public int studentDemands(Long IdStudent,Integer IdUniversity) {
		return userRepository.studentDemands(IdStudent,IdUniversity);
	}
	@Override
	public Map<String, Double> PercentageStudentsByNationality( ) {
		Map<String,Double> Percentages=new HashMap<>();
		List<Double> percent = new ArrayList<>();
		double 	Tunisian= 0 ;
		double  Algerian=0 ;
		double  Canadian=0 ;
		double  German=0 ;
		double  Indian=0 ;
		double  Italian=0;
		double  French =0 ;
		double  English=0 ;
		double  Chinese =0 ;
		double  British=0 ;
		double  other=0;
		List<CandidacyUniversity>candidacyUniversities=(List<CandidacyUniversity>)candidacyRepository.findAllAcceptedDemands();
		for (CandidacyUniversity c : candidacyUniversities){
			if(c.getUser().getNationality().equals(Nationality.Italian)){
				Italian++;
			}else if (c.getUser().getNationality().equals(Nationality.Tunisian)){
				Tunisian++;
			}else if (c.getUser().getNationality().equals(Nationality.Algerian)){
				Algerian++;
			}else if (c.getUser().getNationality().equals(Nationality.German)){
				German++;
			}else if (c.getUser().getNationality().equals(Nationality.French)){
				French++;
			}else if (c.getUser().getNationality().equals(Nationality.Canadian)){
				Canadian++;
			}else if (c.getUser().getNationality().equals(Nationality.Chinese)){
				Chinese++;
			}else if (c.getUser().getNationality().equals(Nationality.British)){
				British++;
			}else if (c.getUser().getNationality().equals(Nationality.Indian)){
				Indian++;
			}else if (c.getUser().getNationality().equals(Nationality.English)){
				English++;
			}else if (c.getUser().getNationality().equals(Nationality.other)){
				other++;
			}
		}
		if(candidacyUniversities.size()!=0){
			System.out.println("Number students :"+candidacyUniversities.size());
			Tunisian =  ((Tunisian/(candidacyUniversities.size()))*100);
			Algerian= ((Algerian/(candidacyUniversities.size()))*100);
			Canadian=((Canadian/(candidacyUniversities.size()))*100);
			German=((German/(candidacyUniversities.size()))*100);
			Indian=((Indian/(candidacyUniversities.size()))*100);
			Italian=((Italian/(candidacyUniversities.size()))*100);
			French=((French/(candidacyUniversities.size()))*100);
			English=((English/(candidacyUniversities.size()))*100);
			Chinese=((Chinese/(candidacyUniversities.size()))*100);
			British=((British/(candidacyUniversities.size()))*100);
			other=((other/(candidacyUniversities.size()))*100);
		}

		percent.add(Tunisian);
		percent.add(Algerian);
		percent.add(Chinese);
		percent.add(Canadian);
		percent.add(French);
		percent.add(English);
		percent.add(German);
		percent.add(Italian);
		percent.add(Indian);
		percent.add(British);
		percent.add(other);

		Percentages.put("Tunisian",Tunisian);
		Percentages.put("Algerian",Algerian);
		Percentages.put("Chinese",Chinese);
		Percentages.put("French",French);
		Percentages.put("English",English);
		Percentages.put("German",German);
		Percentages.put("Indian",Indian);
		Percentages.put("Italian",Italian);
		Percentages.put("British",British);
		Percentages.put("Canadian",Canadian);
		Percentages.put("other",other);

		return Percentages;

	}

}
