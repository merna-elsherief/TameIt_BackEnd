package com.pro.tameit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "specializations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {
    @Id
    @GeneratedValue(generator = "specialization_generator")
    @SequenceGenerator(
            name = "specialization_generator",
            sequenceName = "specialization_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String specializationName;
}
