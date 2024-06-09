package com.pro.tameit.services;

import com.pro.tameit.domain.EAppointmentStatus;
import com.pro.tameit.dto.AppointmentDetailsDTO;
import com.pro.tameit.dto.request.AppointmentDTORequest;
import com.pro.tameit.dto.response.AppointmentDTOResponse;
import com.pro.tameit.models.*;
import com.pro.tameit.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        appointment.setDoctor(doctor);
        appointment.setFees(appointmentDTORequest.getFees());
        appointment.setStatus(EAppointmentStatus.AVAILABLE);
        //first hn4of l clinic 3ndna f l DB or not
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
        appointment.setAppointmentDateTime(appointmentDTORequest.getAppointmentDateTime());
        appointmentRepository.save(appointment);
        return new AppointmentDTOResponse(appointment.getId(),doctor, null, appointment.getClinic(), appointment.getAppointmentDateTime(), EAppointmentStatus.AVAILABLE, appointment.getFees());
    }
    @Override
    public AppointmentDTOResponse updateAppointment(Long id, AppointmentDTORequest appointmentDTORequest){
        //hngeeb l appointment
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        if (appointmentDTORequest.getDoctor()!=null){
            appointment.setDoctor(appointmentDTORequest.getDoctor());
        }
        if (appointmentDTORequest.getPatient()!=null){
            appointment.setPatient(appointmentDTORequest.getPatient());
            appointment.setStatus(EAppointmentStatus.BOOKED);
        }
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
        if (appointmentDTORequest.getAppointmentDateTime()!=null){
            appointment.setAppointmentDateTime(appointmentDTORequest.getAppointmentDateTime());
        }

        if (appointmentDTORequest.getFees()!=null){
            appointment.setFees(appointmentDTORequest.getFees());
        }
        appointmentRepository.save(appointment);
        return new AppointmentDTOResponse(appointment.getId(),appointment.getDoctor(), appointment.getPatient(), appointment.getClinic(), appointment.getAppointmentDateTime(), appointment.getStatus(), appointment.getFees());
    }
    @Override
    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }
    @Override
    public AppointmentDTOResponse book(Long id){
        //hngeeb l appointment
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        //hngeeb l patient:
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientRepository.findByUserId(userName).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        appointment.setPatient(patient);
        appointment.setStatus(EAppointmentStatus.BOOKED);
        appointmentRepository.save(appointment);
        return new AppointmentDTOResponse(appointment.getId(),appointment.getDoctor(), appointment.getPatient(), appointment.getClinic(), appointment.getAppointmentDateTime(), appointment.getStatus(), appointment.getFees());
    }

    //Admin & Patient UI
    @Override
    public List<AppointmentDetailsDTO> getDoctorAppointmentsById(Long id) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByDoctor_Id(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        List<AppointmentDetailsDTO> returnedAppointments = new ArrayList<>();
        for (Appointment appointment:
             appointments) {
            returnedAppointments.add(new AppointmentDetailsDTO(appointment.getDoctor().getFirstName()
                    , appointment.getDoctor().getLastName()
                    , appointment.getPatient().getFirstName()
                    , appointment.getPatient().getLastName()
                    ,appointment.getClinic().getClinicName()
                    ,appointment.getClinic().getAddress()
                    ,appointment.getAppointmentDateTime().getDayOfMonth()
                    ,appointment.getAppointmentDateTime().getDayOfWeek().name()
                    , appointment.getAppointmentDateTime().getMonthValue()
                    , appointment.getAppointmentDateTime().getMonth().name()
                    ,appointment.getAppointmentDateTime().getYear()
                    ,appointment.getAppointmentDateTime().getHour()
                    ,appointment.getAppointmentDateTime().getMinute()
                    ,appointment.getStatus()
                    ,appointment.getFees()));
        }
        return returnedAppointments;
    }
    //Doctor UI
    @Override
    public List<AppointmentDetailsDTO> getDoctorAppointmentsWithoutId() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        Doctor doctor = doctorRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        return getDoctorAppointmentsById(doctor.getId());
    }
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
            returnedAppointments.add(new AppointmentDetailsDTO(appointment.getDoctor().getFirstName()
                    , appointment.getDoctor().getLastName()
                    , appointment.getPatient().getFirstName()
                    , appointment.getPatient().getLastName()
                    ,appointment.getClinic().getClinicName()
                    ,appointment.getClinic().getAddress()
                    ,appointment.getAppointmentDateTime().getDayOfMonth()
                    ,appointment.getAppointmentDateTime().getDayOfWeek().name()
                    , appointment.getAppointmentDateTime().getMonthValue()
                    , appointment.getAppointmentDateTime().getMonth().name()
                    ,appointment.getAppointmentDateTime().getYear()
                    ,appointment.getAppointmentDateTime().getHour()
                    ,appointment.getAppointmentDateTime().getMinute()
                    ,appointment.getStatus()
                    ,appointment.getFees()));
        }
        return returnedAppointments;
    }
    //Patient UI
    @Override
    public List<AppointmentDetailsDTO> getPatientAppointmentsWithoutId() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        Patient patient = patientRepository.findPatientById(user.getId()).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        return getPatientAppointmentsById(patient.getId());
    }
    @Override
    public void deletePatientFromAppointmentsById(Long id) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByPatient_Id(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        for (Appointment appointment:
                appointments) {
            appointment.setStatus(EAppointmentStatus.AVAILABLE);
            appointment.setPatient(null);
            appointmentRepository.save(appointment);
        }
    }
}
