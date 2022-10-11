package com.javachinna.repo;


import com.javachinna.model.Candidacy;
import com.javachinna.model.Profession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoCandidacy extends CrudRepository<Candidacy,Integer> {


      @Query("SELECT c  FROM Candidacy c WHERE CONCAT(c.dateOfCandidacy,c.Status) LIKE %?1%")
     List<Candidacy> findAll(String keyword);

    @Query(value="select d from  Candidacy d join d.offers o  where o.profession=:pro")
    List<Candidacy> offerByProfession(@Param("pro") Profession profession);
}
