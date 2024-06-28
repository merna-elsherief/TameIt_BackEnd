package com.pro.tameit.repo;

import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findPatientById(Long id);
    @Query("select p from Patient p where p.id = ?1")
    Optional<Patient> findUserBPatientId(Long id);
    Optional<List<Patient>> findByFirstName(String firstName);
    @Query("select p from Patient p where p.user.userName = ?1")
    Optional<Patient> findByUserId(String userName);

    @Query("select DISTINCT d from Doctor d JOIN d.appointments a WHERE d.id = a.doctor.id AND  a.patient.id = ?1")
    Optional<List<Doctor>> findMyDoctors(Long id);
}
