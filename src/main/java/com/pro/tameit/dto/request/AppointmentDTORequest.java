package com.pro.tameit.dto.request;

import com.pro.tameit.domain.EAppointmentStatus;
import com.pro.tameit.dto.ClinicDTO;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Patient;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTORequest {
    private Doctor doctor;
    private Patient patient;
    private ClinicDTO clinic;
    @NotNull(message = "Appointment date cannot be null")
    @Future(message = "Appointment date must be in the future")
    private String appointmentDate; // e.g., "2023-05-29"
    private String appointmentTime;
    private EAppointmentStatus status;
    private BigDecimal fees;
    private Boolean isOnline;
}
