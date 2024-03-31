package com.pro.tameit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(generator = "appointment_generator")
    @SequenceGenerator(
            name = "appointment_generator",
            sequenceName = "appointment_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime; //m4 3arfa di htt3ml ezay lsa
    @Column(nullable = false)
    private String status; //3ayza a3mlha "enum"
    @Column(nullable = false)
    private BigDecimal fees;
}
