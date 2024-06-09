package com.pro.tameit.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clinics")
public class Clinic {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String clinicName;

    @Column(nullable = false,unique = false)
    private String address;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @ManyToMany(mappedBy = "clinics")
    private List<Doctor> doctors;
}
