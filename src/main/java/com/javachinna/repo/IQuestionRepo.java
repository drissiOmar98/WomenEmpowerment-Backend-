package com.javachinna.repo;

import com.javachinna.model.QuestionCourses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepo extends CrudRepository<QuestionCourses,Integer> {
}
