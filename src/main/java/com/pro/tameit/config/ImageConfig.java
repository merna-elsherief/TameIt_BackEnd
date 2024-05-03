package com.pro.tameit.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ImageConfig {

    private final String CLOUD_NAME = "di3uxtxzz";
    private final String APT_KEY = "324136194171948";
    private final String API_SECRET ="PRDhJbB4qIpwGlf15k43JrkC8RM";

    @Bean
    public Cloudinary cloudinary(){
        Map<String,String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",APT_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);

    }
}
