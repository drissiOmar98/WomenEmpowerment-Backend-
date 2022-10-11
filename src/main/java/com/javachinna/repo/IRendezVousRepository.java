package com.javachinna.repo;

import com.javachinna.model.Appointment;
import com.javachinna.model.Profession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IRendezVousRepository extends CrudRepository<Appointment, Long> {

    @Query("select count(ren) from Appointment ren join ren.doctor d where d.id=:id")
    int GetNbrAppointmentDoctor(@Param("id") Long idDoctor);

    @Query("select count(c) from Complaint c join c.users d where d.id=:id")
    int GetNbrComplaintDoctor(@Param("id") Long idDoctor);

    @Query("select (count(r)*m.priceconsultation) from Appointment r join r.doctor m where m.id=:id and r.dateApp between :dateD AND :dateF ")
    int GetIncomeDoctor(@Param("id") Long idDoc, @Param("dateD") Date startDate, @Param("dateF") Date endDate);

    @Query("select d.appointmentsDocteur  from User d join d.clinical cl where cl.idClinical=:idCl and d.profession=:prof")
    List<Appointment> getAppointmentByClinicalAndSpeciality(@Param("idCl") Long idClinical, @Param("prof") Profession profession);

    @Query("select a from Appointment a where a.dateApp<:date")
    List<Appointment> DeleteAppointmentAfterfinalDate(@Param("date") Date finalDate);
}
