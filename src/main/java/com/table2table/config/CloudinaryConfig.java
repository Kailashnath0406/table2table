package com.table2table.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dquqsnv8f",
                "api_key", "724341873233381",
                "api_secret", "CDjVrfVckmv_ov74VSOSahKvBEc"
        ));
    }
}
