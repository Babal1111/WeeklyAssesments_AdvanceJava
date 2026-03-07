package com.learning.cms.controllers;

import com.learning.cms.dto.response.ApiResponse;
import com.learning.cms.dto.response.MaterialResponseDTO;
import com.learning.cms.service.CourseMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@Tag(name = "Course Material", description = "APIs for uploading and downloading course materials")
public class CourseMaterialController {

    private final CourseMaterialService materialService;

    @PostMapping("/upload")
    @Operation(summary = "Upload a course material file")
    public ResponseEntity<ApiResponse<MaterialResponseDTO>> uploadMaterial(
            @RequestParam("title") String title,
            @RequestParam("courseId") Long courseId,
            @RequestParam("file") MultipartFile file) {

        MaterialResponseDTO material = materialService.uploadMaterial(title, courseId, file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Material uploaded", material));
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Download a course material by ID")
    public ResponseEntity<Resource> downloadMaterial(@PathVariable Long id) {
        Resource resource = materialService.downloadMaterial(id);
        String contentType = materialService.getContentType(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get all materials for a course")
    public ResponseEntity<ApiResponse<List<MaterialResponseDTO>>> getMaterialsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(ApiResponse.success("Materials fetched", materialService.getMaterialsByCourse(courseId)));
    }
}
