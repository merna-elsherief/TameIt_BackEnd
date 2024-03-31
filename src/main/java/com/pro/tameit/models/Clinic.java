package com.pro.tameit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "clinics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clinic {
    @Id
    @GeneratedValue(generator = "clinic_generator")
    @SequenceGenerator(
            name = "clinic_generator",
            sequenceName = "clinic_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;
    @Column(nullable = false, unique = true)
    private String clinicName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
}
