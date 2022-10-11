package com.javachinna.repo;

import com.javachinna.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface QuestionRepository extends JpaRepository<Question, Long> {


	Question findByquestionId(Long id);
	void deleteByquestionId(Long id);
}
