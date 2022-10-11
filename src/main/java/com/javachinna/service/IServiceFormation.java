package com.javachinna.service;



import com.javachinna.model.*;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface IServiceFormation {

    void ajouterFormateur(User formateur);


    void addFormation(Formation formation);
    void updateFormation(Formation formation, Integer idFormateur);
    void deleteFormation(Integer idFormation);
    List<Formation> afficherFormation();
    List<User> afficherFormateur();
    List<User> afficherApprenant();

    User FormateurwithMaxHo();

     User getFormateurRemunerationMaxSalaire() throws MessagingException;

     TreeMap<Integer, User> getFormateurRemunerationMaxSalaireTrie();

    List<Object> getFormateurRemunerationByDateTrie();

    void CertifactionStudents();


    List<Formation>  SearchMultiple(String key);


    Map<String,Double> PourcentageCoursesByDomain() throws IOException, MessagingException;




    void ajouterApprenant(User apprenant);
    void ajouterEtAffecterFormationAFormateur(Formation formation, Long idFormateur);
    Formation getFile(Integer fileId) throws FileNotFoundException;


    void affecterApprenantFormationWithMax(Long idApprenant, Integer idFormation);

    void affecterApprenantFormation(Long idApprenant,Integer idFormation);




    Integer nbrCoursesParFormateur(Long idF,Date dateDebut, Date dateFin);

    Integer getNbrApprenantByFormation(String title);
    void getNbrApprenantByFormationn();

    Integer getNbrFormationByApprenant(Long idApp,Domain domain ,Date dateDebut, Date dateFin);

    List<Object[]> getNbrApprenantByFormation();




    List<User> getApprenantByFormation(Integer idF);


    Integer getFormateurRemunerationByDate(Long idFormateur, Date dateDebut,Date dateFin);
    Integer getRevenueByFormation(Integer idFormation);



    void likeComments(Integer idC);
    void dislikeComments(Integer idC);


    void FormationWithRate(Integer idF ,Double rate);



    void SearchHistorique(String keyword);



    void addComments(PostComments postComments ,Integer idF,Long idUser);
    void deleteComments(Integer idC);

    PostComments upDateComment (PostComments postComments,Integer idF,Long idUser);

    List<PostComments> getAllComments();



    void decisionUserPUNISHED();

    void LeanerStatus();

    void ListComplete();







}
