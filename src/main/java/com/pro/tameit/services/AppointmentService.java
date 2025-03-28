package com.pro.tameit.services;

import com.pro.tameit.dto.AppointmentDetailsDTO;
import com.pro.tameit.dto.request.AppointmentDTORequest;
import com.pro.tameit.dto.response.AppointmentDTOResponse;
import com.pro.tameit.models.Appointment;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> findAll();

    Optional<Appointment> findById(Long id);

    Appointment save(Appointment appointment);

    void deleteById(Long id);

    AppointmentDTOResponse createAppointment(AppointmentDTORequest appointmentDTORequest);


    AppointmentDetailsDTO updateAppointment(Long id, AppointmentDTORequest appointmentDTORequest);


    @Async
    void deleteAppointmentById(Long id);

    AppointmentDetailsDTO book(Long id);

    //Doctor UI
    List<AppointmentDetailsDTO> getDoctorAppointmentsById(Long id);

    List<AppointmentDetailsDTO> getDoctorAppointmentsWithoutId();


    void deleteDoctorAppointmentsById(Long id);

    List<AppointmentDetailsDTO> getPatientAppointmentsById(Long id);

    List<AppointmentDetailsDTO> getPatientAppointmentsWithoutId();

    //IN Patient UI
    //Delete Appointment
    boolean deletePatientFromAppointmentsById(Long id);

    AppointmentDetailsDTO mapToAppointmentDetailsDTO(Appointment appointment);
}
