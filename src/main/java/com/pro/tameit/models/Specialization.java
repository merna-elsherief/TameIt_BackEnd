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
@Table(name = "specializations")
public class Specialization {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String specializationName;

    @ManyToMany(mappedBy = "specializations")
    private List<Doctor> doctors;
}
