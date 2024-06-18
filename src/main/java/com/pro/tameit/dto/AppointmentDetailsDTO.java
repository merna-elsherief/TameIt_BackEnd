package com.pro.tameit.dto;

import com.pro.tameit.domain.EAppointmentStatus;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Patient;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailsDTO {
    private Long id;
    private String doctorFName;
    private String doctorLName;

    private String patientFName;
    private String patientLName;

    private String clinicName;
    private String clinicAddress;
    private String clinicPhoneNumber;

    //Date & Time
    //Date
    int dayOfMonth;
    String dayOfWeek;

    int monthOfYear;
    String monthNameYear;

    int year;
    //Time
    int hours;

    int minutes;

    private EAppointmentStatus status;
    private BigDecimal fees;
}
