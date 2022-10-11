package com.javachinna.repo;

import com.javachinna.model.PostComments;
import com.javachinna.model.QuizCourses;
import com.javachinna.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javachinna.model.User;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


	User findByEmail(String email);

	boolean existsByEmail(String email);
	@Query("select u from User u where u.profession='Doctor' order by u.Score desc ")
	List<User> classementDoctor();

	/////////////////////////////////   Mahdi User methode       /////////////////////////////
	@Query(value= "select f.title , count(a.id) from Formation f join f.apprenant a group by f")
	List<Object[]> getNbrApprenantByFormation();


	@Query(value="select f.apprenant from  Formation f  where f.idFormation = :id")
	List<User> getRevenueByFormation(@Param("id") Integer idFormation);


	@Query(value = "select f.apprenant from  Formation f  where f.idFormation = :id")
	List<User> getApprenantByFormation(@Param("id") Integer idF );


	@Query(value = "select f.formateur from  Formation f  where f.idFormation = :id")
	List<User> getFormateurByFormation(@Param("id") Integer idF );


	@Query(value= "select SUM(f.nbrHeures*f.formateur.tarifHoraire) from Formation f where f.formateur.id=:id and f.start>=:dateD and f.end<=:dateF")
	Integer getFormateurRemunerationByDate(@Param("id") Integer idFormateur, @Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);


	@Query(value="select count(a.id) from Formation f join f.apprenant a where f.title=:titre")
	Integer getNbrApprenantByFormation(@Param("titre") String titre );


	@Query(value="select f from User f where f.profession='FORMER'")
	List<User> getFormateur();



	@Query(value="select f from User f where f.profession='LEARNER'")
	List<User> getApprenant();



	@Query(value = "select f.formateur from Formation f  where f.formateur.tarifHoraire =(select Max(u.tarifHoraire) from User u where u.profession='FORMER')")
	User FormateurwithMaxHo();



	@Query(value = "select Max(r.totalCorrect) from User u join u.formationA f join f.quizzes q join q.results r  where u.profession='LEARNER'")
	Integer MaxScoreInFormation();


	@Query(value = "select r.sUser from Formation f join f.quizzes q join q.results r where" +
			" r.totalCorrect=(select Max(r.totalCorrect) from User u join u.formationA f join f.quizzes q join q.results r " +
			" where u.profession='LEARNER') and f.idFormation=:id")
	User ApprenentwithMaxScoreInFormation(@Param("id") Integer id);

	@Query(value = "select r.sUser from Result r join r.quiz q join q.formation f where f.idFormation=:id group by r.sUser order by SUM (r.totalCorrect) desc")
	List<User> getApprenantWithScore(@Param("id") Integer id);


	@Query(value = "select r.sUser,SUM (r.totalCorrect) from Result r join r.quiz q join q.formation f where f.idFormation=:id group by r.sUser order by SUM (r.totalCorrect) desc")
	List<Object> getApprenantWithScoreQuiz(@Param("id") Integer id);

	@Query(value = "select count(c.idComn) from PostComments c join c.userC u where c.message='This message was blocked' and u.id=:id " )
	Integer nbrCommentsBadByUser(@Param("id") Long idUser);

	@Query(value = "select c from PostComments c join c.userC u where u.id=:id")
	List<PostComments> CommentsByUser(@Param("id") Long idUser);


	@Query(value = "select r.sUser from Result r join r.quiz q join q.formation f where f.idFormation=:id group by r.sUser order by SUM (r.totalCorrect) desc")
	List<User> getApprenantWithScoreForGifts(@Param("id") Integer id);

	//////////////////////////////             03/19/2022             ///////////////////////////////
	//omar
	@Query(" select s from User s WHERE s.id not in (select r from Rating r)")
	List<User> findStudentWithoutRatings() ;
	//List<User> highestStudentDemands();


	@Query("select u from User u join u.candidacies c where c.partnerInstitution.idPartner=:idUniversity and c.status= 'ACCEPTED'  ")
	List<User>acceptedStudentsByUniversity(@Param("idUniversity") Integer idUniversity);


	@Query(" select s from User s WHERE s.id in (select r from RatingPartner r join r.partnerInstitution w where w.idPartner=:idUniversity) ")
	List<User>findStudentWithRatingByUniversity(@Param("idUniversity") Integer idUniversity);



	@Query("select count(d.idCandidacy) from CandidacyUniversity d where d.user.id=:idStudent and d.partnerInstitution.idPartner=:IdUniversity")
	int studentDemands(@Param("idStudent") Long IdStudent ,@Param("IdUniversity") Integer IdUniversity);




}
