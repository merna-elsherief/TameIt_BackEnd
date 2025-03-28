package com.pro.tameit.repo;

import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Patient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findDoctorById(Long id);

    List<Doctor> findAll(Specification<Doctor> spec);

    List<Doctor> findDoctorByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);
    Optional<List<Doctor>> findByFirstName(String firstName);
    @Query("select d from Doctor d where d.user.id = ?1")
    Optional<Doctor> findByUserId(Long userId);
    @Query("select d from Doctor d where d.user.userName = ?1")
    Optional<Doctor> findByUserName(String useName);
    @Query("select DISTINCT p from Patient p JOIN p.appointments a WHERE p.id = a.patient.id AND  a.doctor.id = ?1")
    Optional<List<Patient>> findMyPatients(Long id);
}
