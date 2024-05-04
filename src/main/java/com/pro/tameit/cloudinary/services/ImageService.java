package com.pro.tameit.cloudinary.services;

import com.pro.tameit.cloudinary.dto.ImageModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ImageService {

    ResponseEntity<Map> uploadImage(ImageModel imageModel);
}
