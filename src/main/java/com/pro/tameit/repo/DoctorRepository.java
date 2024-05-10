package com.pro.tameit.repo;

import com.pro.tameit.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<List<Doctor>> findByFirstName(String firstName);
    @Query("select d from Doctor d where d.user.id = ?1")
    Optional<Doctor> findByUserId(Long userName);
}
