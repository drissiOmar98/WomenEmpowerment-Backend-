package com.javachinna.repo;

import com.javachinna.model.GeographicalArea;
import com.javachinna.model.PartnerInstitution;
import com.javachinna.model.specialty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;

@Repository
@CrossOrigin("http://localhost:4200")
public interface IPartnerRepository extends CrudRepository<PartnerInstitution, Integer> {
    @Query("select p from PartnerInstitution p")
    public List<PartnerInstitution> getAllPartners();

    @Query("select p from PartnerInstitution p where p.geographicalArea=:Area")
    List<PartnerInstitution> FindAllpartnersByArea(  @Param("Area") GeographicalArea Area);

    @Query("select COUNT (*) from PartnerInstitution p where p.special=:s")
    int numPartnerBySpecialty(@Param("s") specialty s) ;

    @Query("select count(d.idCandidacy) from CandidacyUniversity  d  where d.partnerInstitution.idPartner=:id")
    int getNumberOfStudentDemandsByUniversity(@Param("id") Integer idUniversity);


    @Query("select count(d.idCandidacy) from CandidacyUniversity d where d.status='ACCEPTED' and d.partnerInstitution.idPartner=:id")
    int getNumberOfStudentsByUniversity(@Param("id") Integer idUniversity);


    @Query("select count (d.idCandidacy)*d.partnerInstitution.fees from CandidacyUniversity d where d.status='ACCEPTED' and d.partnerInstitution.idPartner=:id and d.DateOFCandidacy>=:dateD and d.DateOFCandidacy<=:dateF " )
    double getUniversityRemunerationByDate(@Param("id") Integer idUniversity, @Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);


    @Query("select p from PartnerInstitution p where p.special=:s and p.geographicalArea=:Area")
    List<PartnerInstitution> FindAllpartnersByAreaAndSpecialty(@Param("Area") GeographicalArea Area,@Param("s") specialty s);

    @Query("select count (d.idCandidacy)*d.partnerInstitution.fees from CandidacyUniversity d where d.status='ACCEPTED'  and d.DateOFCandidacy>=:dateD and d.DateOFCandidacy<=:dateF group by d.partnerInstitution.idPartner")
    List<Object> getUniversitiesRemunerationByDateTrie(@Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);

    @Query("select p from PartnerInstitution p order by p.ratings.size DESC ")
    List<PartnerInstitution> getAllUniversityByTopRating();

    @Query(value = "select u from PartnerInstitution u where concat(u.Name,u.Country,u.Language,u.fees,u.geographicalArea,u.special,u.CapacityReception,u.email) like %?1% order by u.idPartner desc ")
    List<PartnerInstitution> SearchMulti(String keyword);

    @Query("select u.Name,count(c) from CandidacyUniversity c   join c.partnerInstitution u where c.status='ACCEPTED' group by u.idPartner ")
    public List<Object> numberStudentsByUniversity();


    @Query("select  u.Name,count(c.idComment) from CommentUniversity c join c.partnerInstitution u group by u.idPartner")
    public List<Object> numberCommentsByUniversity();

    @Query("select  u.Name,count (r.id) from Rating r join r.partnerInstitution u where r.note<=5 and r.note>3  group by u.idPartner ")
    public List<Object> numberGoodRatingsByUniversity();

    @Query("select  u.Name,count (r.id) from Rating r join r.partnerInstitution u where  r.note<3 group by u.idPartner ")
    public List<Object> numberBadRatingsByUniversity();

    @Query( "SELECT u FROM PartnerInstitution u WHERE  lower(concat(u.Name,u.Country,u.Language,u.fees,u.geographicalArea,u.special,u.CapacityReception,u.email))  like %:keyword%  ")
    List<PartnerInstitution> findUniversityByFilter(@Param("keyword") String keyword);


    @Query( "SELECT u FROM PartnerInstitution u WHERE  lower(u.Name) like %:keyword% or lower(u.Country) like %:keyword% or lower(u.email) like %:keyword% " +
            "or lower(u.CapacityReception) like %:keyword% or lower(u.geographicalArea) like %:keyword% " +
            "or lower(u.special) like %:keyword%  or lower(u.Language) like %:keyword% " +
            "or lower(u.fees) like %:keyword%   ")
    List<PartnerInstitution> findUniversityByFilter1(@Param("keyword") String keyword);








}
