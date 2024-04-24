package com.pro.tameit.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

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
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
}
