package com.pro.tameit.controllers;

import com.pro.tameit.services.UploadImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/image")
public class ImageController {

    private final UploadImage uploadImage;


    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile multipartFile,
                             Model model) throws IOException {
        System.err.println("dfghjk");
        String imageURL = uploadImage.uploadImage(multipartFile);
        model.addAttribute("imageURL",imageURL);
        return "gallery";
    }
}
