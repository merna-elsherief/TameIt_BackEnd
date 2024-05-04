package com.pro.tameit.cloudinary.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.cloudinary.Cloudinary;
import com.pro.tameit.cloudinary.services.UploadImage;

@Service
@RequiredArgsConstructor
public class UploadImageImp implements  UploadImage{

    private final Cloudinary cloudinary;
    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        try {
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("CLOUDINARY_URL=cloudinary://324136194171948:PRDhJbB4qIpwGlf15k43JrkC8RM@di3uxtxzz")
                    .toString();
        }catch (Exception ex) {
            System.err.println(ex);
        }
        return null;
    }
}
