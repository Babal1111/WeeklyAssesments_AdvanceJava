package com.learning.cms.service.impl;

import com.learning.cms.dto.response.MaterialResponseDTO;
import com.learning.cms.entity.Course;
import com.learning.cms.entity.CourseMaterial;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.repository.CourseMaterialRepository;
import com.learning.cms.repository.CourseRepository;
import com.learning.cms.service.CourseMaterialService;
import com.learning.cms.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseMaterialServiceImpl implements CourseMaterialService {

    private final CourseMaterialRepository materialRepository;
    private final CourseRepository courseRepository;
    private final FileStorageUtil fileStorageUtil;

    @Override
    public MaterialResponseDTO uploadMaterial(String title, Long courseId, MultipartFile file) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        String fileName = fileStorageUtil.storeFile(file);

        CourseMaterial material = CourseMaterial.builder()
                .title(title)
                .fileName(fileName)
                .fileType(file.getContentType())
                .fileUrl("/api/materials/download/" + fileName)
                .course(course)
                .build();

        return mapToDTO(materialRepository.save(material));
    }

    @Override
    public Resource downloadMaterial(Long materialId) {
        CourseMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found with id: " + materialId));
        return fileStorageUtil.loadFileAsResource(material.getFileName());
    }

    @Override
    public List<MaterialResponseDTO> getMaterialsByCourse(Long courseId) {
        return materialRepository.findByCourseId(courseId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public String getContentType(Long materialId) {
        CourseMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found with id: " + materialId));
        return material.getFileType() != null ? material.getFileType() : "application/octet-stream";
    }

    private MaterialResponseDTO mapToDTO(CourseMaterial m) {
        return MaterialResponseDTO.builder()
                .id(m.getId())
                .title(m.getTitle())
                .fileName(m.getFileName())
                .fileType(m.getFileType())
                .fileUrl(m.getFileUrl())
                .uploadDate(m.getUploadDate())
                .build();
    }
}
