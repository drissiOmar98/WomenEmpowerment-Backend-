package com.javachinna.repo;

import com.javachinna.model.CandidacyUniversity;
import com.javachinna.model.StatusOfCandidacy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ICandidacyRepository extends CrudRepository<CandidacyUniversity,Integer> {

    //@Query("select d from Candidacy d  join  PartnerInstitution dp where dp.idPartner=id and dp.candidacies.   ")
    //public List<Candidacy> getAllAcceptedDemandByUniversity(Integer idUniversity, @Param("status") StatusOfCandidacy status);
    @Query("select c from CandidacyUniversity c where c.DateOFCandidacy BETWEEN :startDate and :endDate")
    List<CandidacyUniversity> demandByDateOfCreation(@Param("startDate") Date date1, @Param("endDate") Date date2);

    @Query("select c from CandidacyUniversity c where c.status=:s")
    List<CandidacyUniversity>findAllByStatus(@Param("s") StatusOfCandidacy status);


    @Query("select  d.DateOFCandidacy,count(d.idCandidacy) from CandidacyUniversity  d group by d.DateOFCandidacy")
    List<Object> countDemandsByDate();
    @Query("select c from CandidacyUniversity c")
    List<CandidacyUniversity> getAllDemands();


    @Query("select d.DateOFCandidacy,count (d.idCandidacy)from CandidacyUniversity d where d.status='ACCEPTED' group by d.status,d.DateOFCandidacy")
    List<Object[]> countAcceptedDemandByDate();


    @Query("select d.partnerInstitution.Name,count (d.idCandidacy) from CandidacyUniversity d group by d.partnerInstitution")
    List <Object[]> countDemandByUniversity();

    /*@Query("select count (d.idCandidacy) from CandidacyUniversity  d where d.status='ACCEPTED' and d.DateOFCandidacy>=:dateD and d.DateOFCandidacy<=:dateF and d.user.Nationality=:ch")
    List<Object[]> countNumberStudentPerNationalityByYear(@Param("ch")String ch ,@Param("dateD") Date dateDebut,@Param("dateF") Date dateFin);*/

    @Query("select count(d.idCandidacy) from CandidacyUniversity  d where d.status='ACCEPTED' and d.partnerInstitution.idPartner=:id ")
    int countAcceptedDemandByUniversity(@Param("id") Integer idUniversity);


    @Query("select d from CandidacyUniversity d where d.status='WAITING'")
    public List<CandidacyUniversity>findAllWaitingDemands();

    @Query("select d from CandidacyUniversity d where d.status='ACCEPTED'")
    public List<CandidacyUniversity>findAllAcceptedDemands();

    @Query("select d from CandidacyUniversity d where d.status='INPROGRESS'")
    public List<CandidacyUniversity>findAllInProgressDemands();

    @Query("select d from CandidacyUniversity d where d.status not in (select d from CandidacyUniversity d where d.status='WAITING') and d.partnerInstitution.idPartner=:id")
    List<CandidacyUniversity>findAllTreatedByUniversity(@Param("id") Integer idUniversity);

    @Query("select d from CandidacyUniversity d where d.status='ACCEPTED' and d.partnerInstitution.idPartner=:id and  d.DateOFCandidacy>=:dateD and d.dateResponse<=:dateF ")
    public List<CandidacyUniversity>findAllAcceptedDemandsByUniversity(@Param("id")  Integer idUniversity,@Param("dateD") Date dateDebut,@Param("dateF") Date dateFin);

    @Query("select c from CandidacyUniversity c where c.partnerInstitution.idPartner=:id")
    List<CandidacyUniversity> getdemandsByUniversity(@Param("id") Integer idUniversity);

    @Query("select c from CandidacyUniversity c where c.user.id=:id")
    List<CandidacyUniversity> getdemandsByStudent(@Param("id")Long idStudent);










}
