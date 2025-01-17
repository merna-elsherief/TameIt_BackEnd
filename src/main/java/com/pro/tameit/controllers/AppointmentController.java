package com.pro.tameit.controllers;

import com.pro.tameit.dto.request.AppointmentDTORequest;
import com.pro.tameit.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTORequest appointmentDTORequest){
        try {
            return new ResponseEntity<>(appointmentService.createAppointment(appointmentDTORequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating appointment: " + e.getMessage());
        }
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTORequest appointmentDTORequest){
        try {
            return new ResponseEntity<>(appointmentService.updateAppointment(id, appointmentDTORequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error update appointment: " + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointmentById(id);

            return ResponseEntity.ok().body("Appointment deleted successfully.");
        } catch (Exception e) {
            // Log the exception here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the appointment.");
        }
    }
    @PatchMapping("/book/{id}")
    public ResponseEntity<?> bookAppointment(@PathVariable Long id){
        try {
            return new ResponseEntity<>(appointmentService.book(id), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error booking appointment: " + e.getMessage());
        }
    }
    //Admin & Patient UI((Read Appointments of One Doctor By Doctor ID)):
    @GetMapping("/readDoctorAppointments/{id}")
    public ResponseEntity<?> readDoctorAppointmentsById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(appointmentService.getDoctorAppointmentsById(id),HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //Doctor UI ((My Appointments))
    @GetMapping("/readDoctorAppointments")
    public ResponseEntity<?> readDoctorAppointments(){
        try {
            return new ResponseEntity<>(appointmentService.getDoctorAppointmentsWithoutId(),HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //Admin UI ((Read Appointments Of One Patient))
    @GetMapping("/readPatientAppointmentsById/{id}")
    public ResponseEntity<?> readPatientAppointmentsById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(appointmentService.getPatientAppointmentsById(id),HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //Patient UI ((My Appointments))
    @GetMapping("/readPatientAppointments")
    public ResponseEntity<?> readPatientAppointments(){
        try {
            return new ResponseEntity<>(appointmentService.getPatientAppointmentsWithoutId(),HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //Patient UI ((Booking Canceling))
    @PatchMapping("/patientCancelingAppointment/{id}")
    public ResponseEntity<?> patientCancelingAppointment(@PathVariable Long id) {
        try {
            boolean isDeleted = appointmentService.deletePatientFromAppointmentsById(id);

            if (isDeleted) {
                return ResponseEntity.ok().body("Patient successfully removed from appointments.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No appointments found for the given patient ID.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error canceling appointment: " + e.getMessage());
        }
    }

}
