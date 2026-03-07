package com.learning.cms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Online Course Management System API")
                        .description("Spring Boot REST API for managing courses, users, enrollments and materials")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Learning Team")
                                .email("admin@learning.com")));
    }
}
