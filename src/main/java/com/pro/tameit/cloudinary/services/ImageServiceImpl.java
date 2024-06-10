package com.pro.tameit.cloudinary.services;

import com.pro.tameit.cloudinary.dto.ImageModel;
import com.pro.tameit.cloudinary.models.Image;
import com.pro.tameit.cloudinary.repo.ImageRepository;
import com.pro.tameit.models.Doctor;
import com.pro.tameit.models.User;
import com.pro.tameit.repo.DoctorRepository;
import com.pro.tameit.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public ResponseEntity<Map> uploadImage(ImageModel imageModel) {
        try {
            if (imageModel.getName().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (imageModel.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Image image = new Image();
            image.setName(imageModel.getName());
            image.setUrl(cloudinaryService.uploadFile(imageModel.getFile(), "folder_1"));
            if(image.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            imageRepository.save(image);
            return ResponseEntity.ok().body(Map.of("url", image.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    @Override
    public ResponseEntity<Map> changeUserImage(ImageModel imageModel) {
        try {

            Image image = new Image();
            if (imageModel.getName()==null) {
                image.setName("photo"+image.getId());
            }
            image.setUrl(cloudinaryService.uploadFile(imageModel.getFile(), "folder_1"));
            if(image.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUserName(userName)
                    .orElseThrow();
            user.setImage(image);
            imageRepository.save(image);
            userRepository.save(user);
            return ResponseEntity.ok().body(Map.of("url", image.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    @Override
    public ResponseEntity<Map> changeUserImageByDoctorId(Long id, ImageModel imageModel) {
        try {
            Image image = new Image();
            if (imageModel.getName()==null) {
                image.setName("photo"+image.getId());
            }
            image.setUrl(cloudinaryService.uploadFile(imageModel.getFile(), "folder_1"));
            if(image.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
            User user = userRepository.findById(doctor.getUser().getId())
                    .orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
            user.setImage(image);
            imageRepository.save(image);
            userRepository.save(user);
            return ResponseEntity.ok().body(Map.of("url", image.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
