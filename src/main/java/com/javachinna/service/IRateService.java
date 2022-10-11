package com.javachinna.service;

import com.javachinna.model.Rating;

import java.util.List;

public interface IRateService {

    public void addRateAndAffectToStudentAndUniversity(Rating rating, Long idStudent, Integer idUniversity);
    List<Rating> findAllByUniversityId(Integer id);

    List<Rating> getRatingByUniversityAndUser(Integer idUniversity , Long idStudent  );
}
