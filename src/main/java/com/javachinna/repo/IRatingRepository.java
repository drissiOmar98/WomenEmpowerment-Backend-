package com.javachinna.repo;

import com.javachinna.model.RatingPartner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRatingRepository extends CrudRepository<RatingPartner,Integer> {

    @Query("select r from RatingPartner r where r.user.id=:idS and r.partnerInstitution.idPartner=:idU")
    List<RatingPartner> getRatingByUniversityAndUser(@Param("idU") Integer idUniversity,@Param("idS") Long idStudent);



}
