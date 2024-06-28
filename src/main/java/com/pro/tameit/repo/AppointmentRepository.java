package com.pro.tameit.repo;

import com.pro.tameit.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select a from Appointment a where a.doctor.id = ?1")
    Optional<List<Appointment>> findAppointmentsByDoctor_Id(Long doctorId);

    @Query("select a from Appointment a where a.patient.id = ?1")
    Optional<List<Appointment>> findAppointmentsByPatient_Id(Long patientId);
}
