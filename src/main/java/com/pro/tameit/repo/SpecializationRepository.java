package com.pro.tameit.repo;

import com.pro.tameit.models.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Specialization findBySpecializationName(String specializationName);
}
