package com.pro.tameit.services;

import com.pro.tameit.cloudinary.dto.ImageModel;
import com.pro.tameit.cloudinary.services.ImageService;
import com.pro.tameit.domain.ERole;
import com.pro.tameit.dto.SpecializationDTO;
import com.pro.tameit.dto.request.DoctorRequest;
import com.pro.tameit.dto.response.DoctorCardResponse;
import com.pro.tameit.models.Clinic;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Specialization;
import com.pro.tameit.repo.ClinicRepository;
import com.pro.tameit.repo.DoctorRepository;
import com.pro.tameit.repo.SpecializationRepository;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final AuthenticationService authenticationService;
    private final SpecializationRepository specializationRepository;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;
    private final AppointmentService appointmentService;
    private final ClinicService clinicService;
    private final ImageService imageService;
    @Override
    public List<DoctorCardResponse> getAll() {
        return doctorRepository.findAll().stream()
                .map(this::mapToDoctorCardResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<DoctorCardResponse> searchDoctors(String query) {
        List<Doctor> doctors = doctorRepository.findDoctorByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(query, query);
        return doctors.stream()
                .map(this::mapToDoctorCardResponse)
                .collect(Collectors.toList());
    }
//    @Override
//    public List<DoctorCardResponse> sortDoctors(String order) {
//        List<Doctor> doctors = doctorRepository.findAll();
//
//        if (order.equalsIgnoreCase("asc")) {
//            return doctors.stream()
//                    .map(this::mapToDoctorCardResponse)
//                    .sorted()
//                    .collect(Collectors.toList());
//        } else if (order.equalsIgnoreCase("desc")) {
//            return doctors.stream()
//                    .map(this::mapToDoctorCardResponse)
//                    .sorted(Comparator.reverseOrder())
//                    .collect(Collectors.toList());
//        } else {
//            // Invalid order, return empty list
//            return List.of();
//        }
//    }
    @Override
    public String addDoctor(DoctorRequest doctorRequest) {
        //add Doctor To USER DB & DOCTOR DB
        authenticationService.register(doctorRequest.getRegisterRequest(), ERole.DOCTOR);
        //add Doctor details:
        //first get l doctor with the userName
        Doctor doctor = doctorRepository.findByUserName(doctorRequest.getRegisterRequest().getUserName()).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        //Then add doctor details:
        imageService.changeUserImageByDoctorId(doctor.getId(), new ImageModel("photo"+doctor.getId(), doctorRequest.getFile()));
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        doctor.setRating(doctorRequest.getRating());
        doctor.setPhoneNumber(doctorRequest.getPhoneNumber());
        doctor.setPrice(doctorRequest.getPrice());
        doctor.setYearsOfExperience(doctorRequest.getYearsOfExperience());
        doctor.setJobTitle(doctorRequest.getJobTitle());
        doctor.setGender(doctorRequest.getGender());
        doctor.setAbout(doctorRequest.getAbout());
        //Specialization:
        //check If specialization exist or not:
        List<String> doctorRequestSpecializations = doctorRequest.getSpecializations();
        if (doctorRequestSpecializations!=null){
            for (String specializationName:
                 doctorRequestSpecializations) {
                Specialization returnedSpecialization = specializationRepository.findBySpecializationName(specializationName);
                if (returnedSpecialization == null){
                    //h n addoh b2a
                    //h n check if ana asln 3ndy fe specilizations ll doctor da wla l2a:
                    Specialization specialization = Specialization.builder().specializationName(specializationName).build();
                    specializationRepository.save(specialization);
                    if (doctor.getSpecializations()==null){
                        doctor.setSpecializations(new ArrayList<>());
                    }
                    doctor.getSpecializations().add(specialization);
                }else {
                    if (doctor.getSpecializations()==null){
                        doctor.setSpecializations(new ArrayList<>());
                    }
                    doctor.getSpecializations().add(returnedSpecialization);
                }
            }
        }
        //add l clinics b2a:
        List<Clinic> doctorRequestClinics = doctorRequest.getClinics();
        if (doctorRequestClinics!=null){
            for (Clinic clinic:
                 doctorRequestClinics) {
                Clinic returnedClinic = clinicRepository.findByClinicNameContainsIgnoreCase(clinic.getClinicName());
                if (returnedClinic == null){
                    Clinic clinicBuilder = Clinic.builder().clinicName(clinic.getClinicName()).address(clinic.getAddress()).phoneNumber(clinic.getPhoneNumber()).build();
                    clinicRepository.save(clinicBuilder);
                    if (doctor.getClinics()==null){
                        doctor.setClinics(new ArrayList<>());
                    }
                    doctor.getClinics().add(clinicBuilder);
                } else {
                    doctor.getClinics().add(returnedClinic);
                }
            }
        }
        //add doctor image:

        doctorRepository.save(doctor);
        return "Created";
    }
    @Override
    public DoctorCardResponse findDoctorById(Long id) {
        Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        return mapToDoctorCardResponse(doctor);
    }
    @Override
    public void deleteDoctorById(Long id) {
        Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        doctor.getClinics().forEach(clinic -> clinic.getDoctors().remove(doctor));
        // Clear the clinics set in the doctor
        doctor.getClinics().clear();
        doctor.getSpecializations().forEach(specialization -> specialization.getDoctors().remove(doctor));
        // Clear the clinics set in the doctor
        doctor.getSpecializations().clear();
        doctorRepository.deleteById(id);
        userRepository.deleteById(doctor.getUser().getId());
        appointmentService.deleteDoctorAppointmentsById(id);
    }
    @Override
    public String updateDoctor(Long id, DoctorRequest doctorRequest) {
        //add Doctor details:
        //first get l doctor with the userName
        Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        //Then add doctor details:
        if (doctorRequest.getFirstName() != null) {
            doctor.setFirstName(doctorRequest.getFirstName());
        }
        if (doctorRequest.getLastName() != null) {
            doctor.setLastName(doctorRequest.getLastName());
        }
        if (doctorRequest.getPhoneNumber() != null) {
            doctor.setPhoneNumber(doctorRequest.getPhoneNumber());
        }
        if (doctorRequest.getPrice()!=null) {
            doctor.setPrice(doctorRequest.getPrice());
        }
        if (doctorRequest.getYearsOfExperience() != null) {
            doctor.setYearsOfExperience(doctorRequest.getYearsOfExperience());
        }
        if (doctorRequest.getJobTitle() != null) {
            doctor.setJobTitle(doctorRequest.getJobTitle());
        }
        if (doctorRequest.getGender() != null) {
            doctor.setGender(doctorRequest.getGender());
        }
        if (doctorRequest.getAbout()!=null) {
            doctor.setAbout(doctorRequest.getAbout());
        }
        //Specialization:
        //check If specialization exist or not:
        if (doctorRequest.getSpecializations() != null) {
            List<String> doctorRequestSpecializations = doctorRequest.getSpecializations();
            if (doctorRequestSpecializations != null) {
                for (String specializationName :
                        doctorRequestSpecializations) {
                    Specialization returnedSpecialization = specializationRepository.findBySpecializationName(specializationName);
                    if (returnedSpecialization == null) {
                        //h n addoh b2a
                        //h n check if ana asln 3ndy fe specilizations ll doctor da wla l2a:
                        Specialization specialization = Specialization.builder().specializationName(specializationName).build();
                        specializationRepository.save(specialization);
                        if (doctor.getSpecializations() == null) {
                            doctor.setSpecializations(new ArrayList<>());
                        }
                        doctor.getSpecializations().add(specialization);
                    } else {
                        if (doctor.getSpecializations() == null) {
                            doctor.setSpecializations(new ArrayList<>());
                        }
                        if (!doctor.getSpecializations().contains(returnedSpecialization)) {
                            doctor.getSpecializations().add(returnedSpecialization);
                        }
                    }
                }
            }
        }
        //add l clinics b2a:
        if (doctorRequest.getClinics() != null) {
            List<Clinic> doctorRequestClinics = doctorRequest.getClinics();
            if (doctorRequestClinics != null) {
                for (Clinic clinic :
                        doctorRequestClinics) {
                    Clinic returnedClinic = clinicRepository.findByClinicNameContainsIgnoreCase(clinic.getClinicName());
                    if (returnedClinic == null) {
                        Clinic clinicBuilder = Clinic.builder().clinicName(clinic.getClinicName()).address(clinic.getAddress()).phoneNumber(clinic.getPhoneNumber()).build();
                        clinicRepository.save(clinicBuilder);
                        if (doctor.getClinics() == null) {
                            doctor.setClinics(new ArrayList<>());
                        }
                        if (!doctor.getClinics().contains(clinicBuilder)) {
                            doctor.getClinics().add(clinicBuilder);
                        }
                    }else {
                        doctor.getClinics().add(returnedClinic);
                    }
                }
            }
        }
        if (doctorRequest.getFile()!=null){
            imageService.changeUserImageByDoctorId(id, new ImageModel("photo"+doctor.getId(), doctorRequest.getFile()));
        }
        doctorRepository.save(doctor);
        return "Successfully updated :b";
    }
    @Override
    public DoctorCardResponse mapToDoctorCardResponse(Doctor doctor) {
        DoctorCardResponse doctorCardResponse = new DoctorCardResponse();

        doctorCardResponse.setId(doctor.getId());
        doctorCardResponse.setFirstName(doctor.getFirstName());
        doctorCardResponse.setLastName(doctor.getLastName());

        // Handle the image URL, check if the image is not null
        if (doctor.getUser() != null && doctor.getUser().getImage() != null) {
            doctorCardResponse.setImageUrl(doctor.getUser().getImage().getUrl());
        } else {
            doctorCardResponse.setImageUrl(null);
        }
        doctorCardResponse.setRating(doctor.getRating());
        doctorCardResponse.setPrice(doctor.getPrice());
        doctorCardResponse.setPhoneNumber(doctor.getPhoneNumber());
        doctorCardResponse.setYearsOfExperience(doctor.getYearsOfExperience());
        doctorCardResponse.setJobTitle(doctor.getJobTitle());
        //Specialization Mapping:
        List<Specialization> specializationList = doctor.getSpecializations();
        doctorCardResponse.setSpecializations(new ArrayList<>());
        for (Specialization s:
             specializationList) {
            doctorCardResponse.getSpecializations().add(new SpecializationDTO(s.getSpecializationName()));
        }
        //Clinic Mapping
        List<Clinic> clinicList = doctor.getClinics();
        doctorCardResponse.setClinics(new ArrayList<>());
        for (Clinic clinic:
                clinicList) {
            doctorCardResponse.getClinics().add(clinicService.mapToClinicDTO(clinic));
        }
        doctorCardResponse.setAbout(doctor.getAbout());
        return doctorCardResponse;
    }
}
