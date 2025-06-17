package com.kakan.user_service.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "dnpguhdhx");
        config.put("api_key", "437943742342121");
        config.put("api_secret", "k-oL5q7hCA6KtrL8TLRSwcEGG3k");
        return new Cloudinary(config);

    }
}
