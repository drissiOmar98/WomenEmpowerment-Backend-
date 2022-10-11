package com.javachinna.repo;


import com.javachinna.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long > {
    @Query("select r from Rating r where r.user.id=:idS and r.partnerInstitution.idPartner=:idU")
    List<Rating> getRatingByUniversityAndUser(@Param("idU") Integer idUniversity, @Param("idS") Long idStudent);

}
