package com.javachinna.repo;

import com.javachinna.model.Clinical;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClinicalRepository extends CrudRepository<Clinical, Long> {
}
