package com.pro.tameit.services;

import com.pro.tameit.domain.EAppointmentStatus;
import com.pro.tameit.dto.AppointmentDetailsDTO;
import com.pro.tameit.dto.request.AppointmentDTORequest;
import com.pro.tameit.dto.response.AppointmentDTOResponse;
import com.pro.tameit.models.*;
import com.pro.tameit.repo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final PatientRepository patientRepository;
    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }
    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentDTOResponse createAppointment(AppointmentDTORequest appointmentDTORequest) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        Appointment appointment = new Appointment();
        //////////////////////////////////
        appointment.setDoctor(doctor);
        /////////////////////////////////
        appointment.setFees(appointmentDTORequest.getFees());
        appointment.setStatus(EAppointmentStatus.AVAILABLE);
        appointment.setIsOnline(appointmentDTORequest.getIsOnline());
        //first hn4of l clinic 3ndna f l DB or not
        if (!appointmentDTORequest.getIsOnline()) {
            Clinic returnedClinic = clinicRepository.findByClinicNameContainsIgnoreCase(appointmentDTORequest.getClinic().getClinicName());
            if (returnedClinic == null) {
                Clinic clinicBuilder = Clinic.builder().clinicName(appointmentDTORequest.getClinic().getClinicName()).address(appointmentDTORequest.getClinic().getAddress()).phoneNumber(appointmentDTORequest.getClinic().getPhoneNumber()).build();
                clinicRepository.save(clinicBuilder);
                if (doctor.getClinics() == null) {
                    doctor.setClinics(new ArrayList<>());
                }
                doctor.getClinics().add(clinicBuilder);
                appointment.setClinic(clinicBuilder);
                doctorRepository.save(doctor);
            } else {
                appointment.setClinic(returnedClinic);
            }
        } else {
            appointment.setClinic(null);
        }
        /////////////////////////////
        LocalDate appointmentDate = LocalDate.parse(appointmentDTORequest.getAppointmentDate());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime appointmentTime;

        try {
            appointmentTime = LocalTime.parse(appointmentDTORequest.getAppointmentTime(), timeFormatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid time format");
        }

        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);

        /////////////////////////////
        appointment.setAppointmentDateTime(appointmentDateTime);
        appointmentRepository.save(appointment);
        //////////////////////////////
//        doctor.getAppointments().add(appointment);
        return new AppointmentDTOResponse(appointment.getId(),doctor, null, appointment.getClinic(), appointment.getAppointmentDateTime(), EAppointmentStatus.AVAILABLE, appointment.getFees(), appointment.getIsOnline());
    }
    @Override
    public AppointmentDetailsDTO updateAppointment(Long id, AppointmentDTORequest appointmentDTORequest){
        //hngeeb l appointment
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        if (appointmentDTORequest.getDoctor()!=null){
            appointment.setDoctor(appointmentDTORequest.getDoctor());
        }
        if (appointmentDTORequest.getPatient()!=null){
            appointment.setPatient(appointmentDTORequest.getPatient());
            appointment.setStatus(EAppointmentStatus.BOOKED);
        }
        if (appointmentDTORequest.getIsOnline()!=null){
            appointment.setIsOnline(appointmentDTORequest.getIsOnline());
            if (!appointmentDTORequest.getIsOnline()){
                if (appointmentDTORequest.getClinic()!=null){
                    Doctor doctor = appointment.getDoctor();
                    Clinic returnedClinic = clinicRepository.findByClinicNameContainsIgnoreCase(appointmentDTORequest.getClinic().getClinicName());
                    if (returnedClinic == null){
                        Clinic clinicBuilder = Clinic.builder().clinicName(appointmentDTORequest.getClinic().getClinicName()).address(appointmentDTORequest.getClinic().getAddress()).phoneNumber(appointmentDTORequest.getClinic().getPhoneNumber()).build();
                        clinicRepository.save(clinicBuilder);
                        if (doctor.getClinics()==null){
                            doctor.setClinics(new ArrayList<>());
                        }
                        doctor.getClinics().add(clinicBuilder);
                        doctorRepository.save(doctor);
                    }
                }
            }
        }
        if (appointmentDTORequest.getAppointmentDate()!=null||appointmentDTORequest.getAppointmentTime()!=null){
            /////////////////////////////
            LocalDate appointmentDate = LocalDate.parse(appointmentDTORequest.getAppointmentDate());
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
            LocalTime appointmentTime;

            try {
                appointmentTime = LocalTime.parse(appointmentDTORequest.getAppointmentTime(), timeFormatter);
            } catch (DateTimeParseException e) {
                throw new RuntimeException("Invalid time format");
            }

            LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);

            /////////////////////////////
            appointment.setAppointmentDateTime(appointmentDateTime);
        }

        if (appointmentDTORequest.getFees()!=null){
            appointment.setFees(appointmentDTORequest.getFees());
        }
        appointmentRepository.save(appointment);

        return mapToAppointmentDetailsDTO(appointment);
    }
    @Override
    public boolean deleteAppointmentById(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public AppointmentDetailsDTO book(Long id){
        //hngeeb l appointment
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        //hngeeb l patient:
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientRepository.findByUserId(userName).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        appointment.setPatient(patient);
        appointment.setStatus(EAppointmentStatus.BOOKED);
        appointmentRepository.save(appointment);
        return mapToAppointmentDetailsDTO(appointment);
    }

    //Admin & Patient UI
    @Override
    public List<AppointmentDetailsDTO> getDoctorAppointmentsById(Long id) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByDoctor_Id(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        List<AppointmentDetailsDTO> returnedAppointments = new ArrayList<>();
        for (Appointment appointment:
             appointments) {
            returnedAppointments.add(mapToAppointmentDetailsDTO(appointment));
        }
        return returnedAppointments;
    }
    //Doctor UI
    //Doctor My Appointments
    @Override
    public List<AppointmentDetailsDTO> getDoctorAppointmentsWithoutId() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        return getDoctorAppointmentsById(doctor.getId());
    }
    //Use it in delete Doctor
    @Override
    public void deleteDoctorAppointmentsById(Long id) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByDoctor_Id(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        for (Appointment appointment:
                appointments) {
            appointmentRepository.deleteById(appointment.getId());
        }
    }
    //Admin UI
    @Override
    public List<AppointmentDetailsDTO> getPatientAppointmentsById(Long id) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByPatient_Id(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        List<AppointmentDetailsDTO> returnedAppointments = new ArrayList<>();
        for (Appointment appointment:
                appointments) {
            returnedAppointments.add(mapToAppointmentDetailsDTO(appointment));
        }
        return returnedAppointments;
    }
    //Patient UI
    //Patient My Appointments
    @Override
    public List<AppointmentDetailsDTO> getPatientAppointmentsWithoutId() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        Patient patient = patientRepository.findPatientById(user.getId()).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        return getPatientAppointmentsById(patient.getId());
    }
    //IN Patient UI
    //Delete Appointment
    @Override
    public boolean deletePatientFromAppointmentsById(Long id) {
        try {
            List<Appointment> appointments = appointmentRepository.findAppointmentsByPatient_Id(id)
                    .orElseThrow(() -> new RuntimeException("No appointments found for the given patient ID."));

            if (appointments.isEmpty()) {
                return false;
            }

            for (Appointment appointment : appointments) {
                appointment.setStatus(EAppointmentStatus.AVAILABLE);
                appointment.setPatient(null);
                appointmentRepository.save(appointment);
            }

            return true;
        } catch (Exception e) {
            // Log the exception here
            return false;
        }
    }
    @Override
    public AppointmentDetailsDTO mapToAppointmentDetailsDTO(Appointment appointment) {
        AppointmentDetailsDTO appointmentDetailsDTO = new AppointmentDetailsDTO();
        appointmentDetailsDTO.setId(appointment.getId());
        appointmentDetailsDTO.setDoctorFName(appointment.getDoctor().getFirstName());
        appointmentDetailsDTO.setDoctorLName(appointment.getDoctor().getLastName());

        if (appointment.getPatient() != null && appointment.getPatient().getFirstName() != null
                && appointment.getPatient().getLastName() != null) {
            appointmentDetailsDTO.setPatientFName(appointment.getPatient().getFirstName());
            appointmentDetailsDTO.setPatientLName(appointment.getPatient().getLastName());
        } else {
            appointmentDetailsDTO.setPatientFName(null);
            appointmentDetailsDTO.setPatientLName(null);
        }

        if (appointment.getIsOnline()) {
            appointmentDetailsDTO.setClinicName(null);
            appointmentDetailsDTO.setClinicAddress(null);
            appointmentDetailsDTO.setClinicPhoneNumber(null);
        } else {
            appointmentDetailsDTO.setClinicName(appointment.getClinic().getClinicName());
            appointmentDetailsDTO.setClinicAddress(appointment.getClinic().getAddress());
            appointmentDetailsDTO.setClinicPhoneNumber(appointment.getClinic().getPhoneNumber());
        }

        appointmentDetailsDTO.setIsOnline(appointment.getIsOnline());

        appointmentDetailsDTO.setDayOfMonth(appointment.getAppointmentDateTime().getDayOfMonth());
        appointmentDetailsDTO.setDayOfWeek(appointment.getAppointmentDateTime().getDayOfWeek().name());

        appointmentDetailsDTO.setMonthOfYear(appointment.getAppointmentDateTime().getMonthValue());
        appointmentDetailsDTO.setMonthNameYear(appointment.getAppointmentDateTime().getMonth().name());

        appointmentDetailsDTO.setYear(appointment.getAppointmentDateTime().getYear());
        appointmentDetailsDTO.setHours(appointment.getAppointmentDateTime().getHour());
        appointmentDetailsDTO.setMinutes(appointment.getAppointmentDateTime().getMinute());

        appointmentDetailsDTO.setStatus(appointment.getStatus());
        appointmentDetailsDTO.setFees(appointment.getFees());

        return appointmentDetailsDTO;
    }
}
