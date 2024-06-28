package com.pro.tameit.cloudinary;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name_image")
    private String name;

    @Column(name = "url_image")
    private String url;
}
