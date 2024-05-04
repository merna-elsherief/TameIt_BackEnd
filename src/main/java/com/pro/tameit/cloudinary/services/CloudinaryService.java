package com.pro.tameit.cloudinary.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String uploadFile(MultipartFile file, String folderName) throws IOException;
}
