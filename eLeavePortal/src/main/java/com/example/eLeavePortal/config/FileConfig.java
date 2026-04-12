package com.example.eLeavePortal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Value("${app.storage.pfp}")
    private String pfpPath;

    @Value("${app.storage.files}")
    private String filesPath;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        registry.addResourceHandler("/pfp/**")
                .addResourceLocations("file:" + pfpPath + "/");

        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + filesPath + "/");
    }
}
