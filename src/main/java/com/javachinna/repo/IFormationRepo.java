package com.javachinna.repo;


import com.javachinna.model.Domain;
import com.javachinna.model.Formation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;


@Repository
public interface IFormationRepo extends CrudRepository<Formation,Integer> {



    @Query(value= "select SUM(f.nbrHeures*f.formateur.tarifHoraire) from Formation f where f.formateur.id=:id and f.start>=:dateD and f.end<=:dateF")
    Integer getFormateurRemunerationByDate(@Param("id") Long idFormateur, @Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);

    @Query(value= "select SUM(f.nbrHeures*f.formateur.tarifHoraire) ,f.formateur from Formation f where f.start>=:dateD and f.end<=:dateF group by f.formateur order by SUM(f.nbrHeures*f.formateur.tarifHoraire) desc ")
    List<Object> getFormateurRemunerationByDateTrie(@Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);


    @Query(value= "select SUM(f.nbrHeures*f.formateur.tarifHoraire) from Formation f where f.formateur.id=:id and f.formateur.profession='FORMER'")
    Integer getFormateurRemuneration(@Param("id") Long idFormateur);

    @Query(value="select count(a.id) from Formation f join f.apprenant a where f.title=:titre")
    Integer getNbrApprenantByFormation(@Param("titre") String titre);

    @Query(value="select count(a.id) from Formation f join f.apprenant a where f.idFormation=:id")
    Integer getNbrApprenantByFormationId(@Param("id") Integer id);

    @Query(value = "select count(f.idFormation) from Formation f join f.apprenant a where a.id=:id and f.start>=:dateD and f.end<=:dateF and f.domain=:domain")
    Integer getNbrFormationByApprenant(@Param("id") Long idApp, @Param("domain") Domain domain, @Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);


  //  @Query(value = "select f from Formation f where concat(f.title,f.level,f.domain,f.frais,f.nbrHeures,f.nbrMaxParticipant) like %?1% group by f order by sum(f.likes-f.dislikes) desc")
    @Query(value = "select f from Formation f where concat(f.title,f.level,f.domain,f.frais,f.nbrHeures,f.nbrMaxParticipant) like %?1% group by f order by f.Rating desc")
    List<Formation> rech(String keyword);


    @Query(value = "select f from Formation f where f.start>=:dateD and f.end<=:dateF")
    List<Formation> listformationByDate(@Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);

    @Query(value = "select f from Formation f where f.domain=:domain")
    List<Formation> listFormationByDomain(@Param("domain") Domain domain);

    @Query(value = "select f from Formation f join f.apprenant a where a.id=:id")
    List<Formation> listFormationParApprenant(@Param("id") Long idApp);




    @Query(value = "select count(f.idFormation) from Formation f join f.formateur fr where f.start>=:dateD and f.end<=:dateF and fr.id=:id")
    Integer nbrCoursesParFormateur(@Param("id") Long idF, @Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);

}
