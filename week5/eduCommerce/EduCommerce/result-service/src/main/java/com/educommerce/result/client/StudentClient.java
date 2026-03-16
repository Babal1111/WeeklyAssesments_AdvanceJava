package com.educommerce.result.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service")
public interface StudentClient {

    @GetMapping("/students/{id}")
    StudentResponse getStudentById(@PathVariable("id") Long id);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class StudentResponse {
        private Long id;
        private String name;
        private String email;
        private String department;
        private Integer semester;
    }
}
