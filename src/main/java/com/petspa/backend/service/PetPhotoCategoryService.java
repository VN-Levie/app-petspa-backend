package com.petspa.backend.service;

import com.petspa.backend.dto.PetPhotoCategoryDTO;
import com.petspa.backend.entity.PetPhotoCategory;
import com.petspa.backend.repository.PetPhotoCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetPhotoCategoryService {

    @Autowired
    private PetPhotoCategoryRepository petPhotoCategoryRepository;

    // Lấy tất cả loại ảnh
    public List<PetPhotoCategoryDTO> getAllPhotoCategories() {
        return petPhotoCategoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Chuyển đổi từ PetPhotoCategory sang PetPhotoCategoryDTO
    private PetPhotoCategoryDTO convertToDTO(PetPhotoCategory petPhotoCategory) {
        PetPhotoCategoryDTO dto = new PetPhotoCategoryDTO();
        dto.setId(petPhotoCategory.getId());
        dto.setName(petPhotoCategory.getName());
        dto.setDescription(petPhotoCategory.getDescription());
        return dto;
    }

    // Lấy loại ảnh theo ID
    public PetPhotoCategoryDTO getPhotoCategoryById(Long id) {
        return petPhotoCategoryRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Thêm loại ảnh mới
    public PetPhotoCategoryDTO addPhotoCategory(PetPhotoCategoryDTO petPhotoCategoryDTO) {
        PetPhotoCategory petPhotoCategory = new PetPhotoCategory();
        petPhotoCategory.setName(petPhotoCategoryDTO.getName());
        petPhotoCategory.setDescription(petPhotoCategoryDTO.getDescription());
        petPhotoCategory = petPhotoCategoryRepository.save(petPhotoCategory);
        return convertToDTO(petPhotoCategory);
    }

    // Cập nhật loại ảnh
    public PetPhotoCategoryDTO updatePhotoCategory(Long id, PetPhotoCategoryDTO petPhotoCategoryDTO) {
        PetPhotoCategory petPhotoCategory = petPhotoCategoryRepository.findById(id).orElse(null);
        if (petPhotoCategory == null) {
            return null;
        }
        petPhotoCategory.setName(petPhotoCategoryDTO.getName());
        petPhotoCategory.setDescription(petPhotoCategoryDTO.getDescription());
        petPhotoCategory = petPhotoCategoryRepository.save(petPhotoCategory);
        return convertToDTO(petPhotoCategory);
    }

    // Xóa loại ảnh
    public void deletePhotoCategory(Long id) {
        petPhotoCategoryRepository.deleteById(id);
    }


}
