package com.pro.tameit.repo;

import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Patient;
import com.pro.tameit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<List<Patient>> findByFirstName(String firstName);
    @Query("select p from Patient p where p.user.userName = ?1")
    Optional<Patient> findByUserId(String userName);


}
