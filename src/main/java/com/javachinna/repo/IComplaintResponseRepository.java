package com.javachinna.repo;

import com.javachinna.model.ComplaintResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IComplaintResponseRepository extends CrudRepository<ComplaintResponse, Long> {
}
