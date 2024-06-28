package com.pro.tameit.dao;

import com.pro.tameit.models.Patient;
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
public class PatientSearchDao {
    private final EntityManager entityManager;
    public List<Patient> findAllByCriteria(
            PatientSearchRequest patientSearchRequest
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Patient> criteriaQuery = criteriaBuilder.createQuery(Patient.class);
        List<Predicate> predicates = new ArrayList<>();
        //Select * From Patients
        Root<Patient> root = criteriaQuery.from(Patient.class);
        if (patientSearchRequest.getFirstName() != null){
            Predicate firstNamePredicate = criteriaBuilder
                    .like(root.get("firstName"), "%"+ patientSearchRequest.getFirstName()+"%");
            predicates.add(firstNamePredicate);
        }
        if (patientSearchRequest.getFirstName() != null){
            Predicate lastNamePredicate = criteriaBuilder
                    .like(root.get("lastName"), "%"+ patientSearchRequest.getLastName()+"%");
            predicates.add(lastNamePredicate);
        }
        criteriaQuery.where(
                criteriaBuilder.or(predicates.toArray(new Predicate[0]))
        );
        TypedQuery<Patient> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
