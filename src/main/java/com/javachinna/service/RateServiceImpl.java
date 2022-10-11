package com.javachinna.service;

import com.javachinna.model.PartnerInstitution;
import com.javachinna.model.Profession;
import com.javachinna.model.Rating;
import com.javachinna.model.User;
import com.javachinna.repo.IPartnerRepository;
import com.javachinna.repo.RatingRepository;
import com.javachinna.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RateServiceImpl implements IRateService{


    @Autowired
    RatingRepository raterepo;

    @Autowired
    UserRepository userrepo;

    @Autowired
    IPartnerRepository partnerrepo;

    @Override
    @Transactional
    public void addRateAndAffectToStudentAndUniversity(Rating rating, Long idStudent, Integer idUniversity) {
        User student = userrepo.findById(idStudent).orElse(null);
        PartnerInstitution university=partnerrepo.findById(idUniversity).orElse(null);
        assert student != null;
        int b = student.getProfession().compareTo(Profession.STUDENT);
        if(b==0){
            rating.setUser(student);
            rating.setPartnerInstitution(university);
            raterepo.save(rating);
        }


    }




    @Override
    public List<Rating> findAllByUniversityId(Integer id) {
        return null;
    }

    @Override
    public List<Rating> getRatingByUniversityAndUser(Integer idUniversity, Long idStudent) {
        return raterepo.getRatingByUniversityAndUser(idUniversity,idStudent);
    }
}
