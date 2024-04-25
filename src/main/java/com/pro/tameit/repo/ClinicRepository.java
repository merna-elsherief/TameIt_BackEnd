package com.pro.tameit.repo;

import com.pro.tameit.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findByClinicNameContainsIgnoreCase(String clinicName);
}
