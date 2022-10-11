package com.javachinna.repo;

import com.javachinna.model.Result;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResultRepo extends CrudRepository<Result,Integer> {

    Object findAll(Sort totalCorrect);

    @Query(value = "select count(r.id) from Result r join r.sUser u join r.quiz q where u.id=:idu and q.idQuiz=:idq and r.status=false")
    Integer findUserQuiz(@Param("idu") Long idU,@Param("idq") Integer idQ);


    @Query(value = "select count(r.id) from Result r join r.sUser u where u.id=:idu")
    Integer getNbrQuiz(@Param("idu") Long idU);

    @Query(value = "select sum(r.totalCorrect) from Result r join r.sUser u where u.id=:idu group by r.sUser ")
    Integer getScore(@Param("idu") Long idU);

    @Query(value = "select r from Formation f join f.quizzes q join q.results r join r.sUser u where u.id=:idu and f.idFormation=:idf and r.status=false and f.end < current_date  group by r")
    List<Result>  getResultByIdUAndAndIdF(@Param("idu") Long idU, @Param("idf") Integer idF);

    @Query(value = "select r from Result r join r.quiz q join q.formation f where f.idFormation=:idf group by r.sUser order by SUM (r.totalCorrect) desc")
    List<Result> getQuizResultByFormer(@Param("idf") Integer idF);

    @Query(value = "select count(r.id) from Result r join r.quiz q join q.formation f where f.idFormation=:idf group by r.sUser order by SUM (r.totalCorrect) desc")
    Integer nbrResultByCourses(@Param("idf") Integer idF);

    @Query(value = "select r from Result r join r.quiz q where q.idQuiz=:idq")
    List<Result> getResultByQuizCourses(@Param("idq") Integer idQ);


}
