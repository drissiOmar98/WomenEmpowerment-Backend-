package com.javachinna.service;

import com.javachinna.model.Publicity;
import com.javachinna.repo.IPublicityRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
@Slf4j

public class PublicityService implements IPublicityService {

    @Autowired
    IPublicityRepo iPublicityRepo;

    @Override
    public Publicity AddPublicity(Publicity publicity) {
        return iPublicityRepo.save(publicity);
    }

    @Override
    public List<Publicity> getAllPublicities() {

        return (List<Publicity>) iPublicityRepo.findAll();

    }

    @Override
    public void deletePublicity(Integer idPublicity) {

        log.info("In methode deletePublicity");
        log.warn("Are you sure you want to delete Publicity");

        iPublicityRepo.deleteById(idPublicity);
        log.error("exeption");

    }

    @Override
    public Publicity upDatePublicity(Publicity publicity) {
        return iPublicityRepo.save(publicity);

    }
}
