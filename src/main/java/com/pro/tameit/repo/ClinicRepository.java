package com.pro.tameit.repo;

import com.pro.tameit.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Clinic findByClinicName(String clinicName);
}
