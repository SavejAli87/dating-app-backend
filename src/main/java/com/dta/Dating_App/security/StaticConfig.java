package com.dta.Dating_App.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class StaticConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 👉 Local folder ko URL se bind kar rahe hai
        String uploadPath = Paths.get("amara").toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/amara/**")
                .addResourceLocations(uploadPath);
    }
}

