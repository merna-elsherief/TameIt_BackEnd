package com.pro.tameit.services;

import com.pro.tameit.domain.ERole;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.Message;
import com.pro.tameit.models.Patient;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.DoctorRepository;
import com.pro.tameit.repo.MessageRepository;
import com.pro.tameit.repo.PatientRepository;
import com.pro.tameit.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService{
    private final UserRepository userRepository;
    private final MessageRepository chatMessageRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public Message saveMessage(Message message) {
        User sender = userRepository.findById(message.getSenderId()).orElseThrow(()->new RuntimeException("Please provide an valid userName!"));
        if (sender.getRole()== ERole.DOCTOR){
            //then receiverId is patient id
            Patient patient = patientRepository.findPatientById(message.getReceiverId()).orElseThrow(()->new RuntimeException("Please provide an valid userName!"));
            message.setReceiverId(patient.getUser().getId());
        } else if (sender.getRole()== ERole.PATIENT) {
            //then receiverId is doctor id
            Doctor doctor = doctorRepository.findDoctorById(message.getReceiverId()).orElseThrow(()->new RuntimeException("Please provide an valid userName!"));
            message.setReceiverId(doctor.getUser().getId());
        }
        message.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        User user = userRepository.findById(senderId).orElseThrow(()->new RuntimeException("Please provide an valid userName!"));
        if (user.getRole() == ERole.DOCTOR){
            //then receiverId is patient id
            Patient patient = patientRepository.findPatientById(receiverId).orElseThrow(()->new RuntimeException("Please provide an valid userName!"));
            receiverId = patient.getUser().getId();
        } else if (user.getRole() == ERole.PATIENT) {
            //then receiverId is doctor id
            Doctor doctor = doctorRepository.findDoctorById(receiverId).orElseThrow(()->new RuntimeException("Please provide an valid userName!"));
            receiverId = doctor.getUser().getId();
        }
        return chatMessageRepository.findBySenderIdAndReceiverId(senderId, receiverId).stream()
                .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getChatHistoryForUser(Long userId) {
        return chatMessageRepository.findByReceiverId(userId);
    }
}

