package com.javachinna.repo;

import com.javachinna.model.Certificat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICertificatRepo extends CrudRepository<Certificat,Long> {
}
