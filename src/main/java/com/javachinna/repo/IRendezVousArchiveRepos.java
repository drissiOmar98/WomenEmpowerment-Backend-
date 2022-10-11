package com.javachinna.repo;

import com.javachinna.model.ArchiveAppointment;
import org.springframework.data.repository.CrudRepository;

public interface IRendezVousArchiveRepos  extends CrudRepository<ArchiveAppointment,Long> {
}
