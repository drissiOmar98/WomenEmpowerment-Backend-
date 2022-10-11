package com.javachinna.repo;

import com.javachinna.model.Complaint;
import com.javachinna.model.Formation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IReclamtionRepository extends CrudRepository<Complaint, Long> {
   // @Query("select count(c) from Complaint c join c.doctor d where d.idUser=:id")
  // int  GetNbrComplaintsTodoctor(@Param("id") Long idDoctor);


   @Query(value = "select c from Complaint c where concat(c.idCom,c.dateCom,c.description,c.type) like %?1% group by c ")
   List<Complaint> searchmultilplcomplaint(String keyword);
}
