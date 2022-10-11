package com.javachinna.service;

import com.javachinna.model.RatingPartner;
import com.javachinna.model.TypeRating;

import java.util.List;

public interface IRatingService {
    public void addRating (RatingPartner ratingPartner);
    public void addRateAndAffectToStudentAndUniversity(TypeRating typeRating, Long idStudent, Integer idUniversity);
    List<RatingPartner> getRatingByUniversityAndUser(Integer idUniversity , Long idStudent  );

   // public String addReputationRates(Integer idRate, Integer idUniversity);

}
