package com.pro.tameit.dto;

import com.pro.tameit.domain.EAppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private Boolean isOnline;
}
