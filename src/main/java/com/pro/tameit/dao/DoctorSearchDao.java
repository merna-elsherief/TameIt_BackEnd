package com.pro.tameit.dao;

import com.pro.tameit.domain.DoctorJobTitle;
import com.pro.tameit.domain.EGender;
import com.pro.tameit.models.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DoctorSearchDao {
    private final EntityManager entityManager;
    public List<Doctor> findAllBySimpleQuery(
            String firstName,
            String lastName,
            EGender gender,
            DoctorJobTitle jobTitle
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);

        //Select * From Doctors
        Root<Doctor> root = criteriaQuery.from(Doctor.class);

        //prepare WHERE Clause
        //WHERE firstName like '%merna%'
        Predicate firstNamePredicate = criteriaBuilder
                .like(root.get("firstName"), "%"+firstName+"%");
        Predicate lastNamePredicate = criteriaBuilder
                .like(root.get("lastName"), "%"+lastName+"%");
        Predicate firstNameOrLastNamePredicate = criteriaBuilder.or(
                firstNamePredicate,
                lastNamePredicate
        );
        Predicate genderPredicate = criteriaBuilder
                .like(root.get("gender"), "%"+gender+"%");
        Predicate jobTitlePredicate = criteriaBuilder
                .like(root.get("jobTitle"), "%"+jobTitle+"%");
        // => Final Query ==> Select * From Doctors Where firstName like '%ali%'
        // or lastName like '%ali%'
        criteriaQuery.where(firstNameOrLastNamePredicate);
        TypedQuery<Doctor> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Doctor> findAllByCriteria(
            DoctorSearchRequest doctorSearchRequest
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
        List<Predicate> predicates = new ArrayList<>();
        //Select * From Doctors
        Root<Doctor> root = criteriaQuery.from(Doctor.class);
        if (doctorSearchRequest.getFirstName() != null){
            Predicate firstNamePredicate = criteriaBuilder
                    .like(root.get("firstName"), "%"+ doctorSearchRequest.getFirstName()+"%");
            predicates.add(firstNamePredicate);
        }
        if (doctorSearchRequest.getFirstName() != null){
            Predicate lastNamePredicate = criteriaBuilder
                    .like(root.get("lastName"), "%"+ doctorSearchRequest.getLastName()+"%");
            predicates.add(lastNamePredicate);
        }
        criteriaQuery.where(
                criteriaBuilder.or(predicates.toArray(new Predicate[0]))
        );
        TypedQuery<Doctor> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
