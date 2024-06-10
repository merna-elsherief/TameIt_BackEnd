package com.pro.tameit.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pro.tameit.domain.DoctorJobTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import com.pro.tameit.domain.EGender;

import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctors")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Doctor {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private EGender gender;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_specialization",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "specialization_id")}
    )
    private List<Specialization> specializations = new ArrayList<>();;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_clinic",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "clinic_id")}
    )
    private List<Clinic> clinics = new ArrayList<>();

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = true,unique = true)
    private String phoneNumber;

    @Column(nullable = true)
    private Integer price;

    @Column(nullable = true)
    private Integer rating;

    @Column(nullable = true)
    private Integer yearsOfExperience;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private DoctorJobTitle jobTitle;

    private String about;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments;
}
