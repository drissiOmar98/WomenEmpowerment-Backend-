package com.javachinna.repo;


import com.javachinna.model.QuestionCandidacy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import com.javachinna.model.QuestionCourses;

@Repository
public interface RepoQuestionCandidacy extends CrudRepository<QuestionCandidacy, Integer> {
}
