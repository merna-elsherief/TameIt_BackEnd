package com.pro.tameit.cloudinary.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
public interface UploadImage {
    String uploadImage(MultipartFile multipartFile) throws IOException;
}
