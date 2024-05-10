package com.pro.tameit.cloudinary.repo;

import com.pro.tameit.cloudinary.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
