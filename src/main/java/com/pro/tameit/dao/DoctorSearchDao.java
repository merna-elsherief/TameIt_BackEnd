//package com.pro.tameit.dao;
//
//import com.pro.tameit.models.Doctor;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class DoctorSearchDao {
//    private final EntityManager entityManager;
//    public List<Doctor> findAllBySimpleQuery(
//            String firstName,
//            String lastName
//    ) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
//
//        //Select * From Doctors
//        Root<Doctor> root = criteriaQuery.from(Doctor.class);
//
//        //prepare WHERE Clause
//        //WHERE firstName like '%merna%'
//        Predicate firstNamePredicate = criteriaBuilder
//                .like(root.get("firstName"), "%"+firstName+"%");
//        Predicate lastNamePredicate = criteriaBuilder
//                .like(root.get("lastName"), "%"+lastName+"%");
//
//    }
//}
