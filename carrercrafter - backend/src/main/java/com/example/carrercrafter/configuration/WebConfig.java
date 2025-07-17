package com.example.carrercrafter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve resumes from: http://localhost:8081/uploads/resumes/filename.pdf
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

}
