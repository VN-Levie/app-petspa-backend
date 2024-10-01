package com.petspa.backend.service;

import com.petspa.backend.dto.CategoryDTO;
import com.petspa.backend.dto.SpaCategoryDTO;
import com.petspa.backend.dto.SpaProductDTO;
import com.petspa.backend.entity.Category;
import com.petspa.backend.entity.SpaCategory;
import com.petspa.backend.repository.CategoryRepository;
import com.petspa.backend.repository.SpaCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpaCaregoryService {

    @Autowired
    private SpaCategoryRepository spaCategoryRepo;

    // Lấy tất cả danh mục
    public List<SpaCategoryDTO> getAllCategories() {
        return spaCategoryRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm danh mục mới
    public SpaCategoryDTO addCategory(SpaCategoryDTO categoryDTO) {
        SpaCategory category = convertToEntity(categoryDTO);
        category = spaCategoryRepo.save(category);
        return convertToDTO(category);
    }

    // Cập nhật danh mục
    public SpaCategoryDTO updateCategory(Long id, SpaCategoryDTO categoryDTO) {
        Optional<SpaCategory> existingCategory = spaCategoryRepo.findById(id);
        if (existingCategory.isPresent()) {
            SpaCategory category = existingCategory.get();
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            category = spaCategoryRepo.save(category);
            return convertToDTO(category);
        }
        return null;
    }

    // Xóa danh mục
    public void deleteCategory(Long id) {
        spaCategoryRepo.softDelete(id);
    }

    //count all
    public Long countAll() {
        return spaCategoryRepo.countAll();
    }

    

    //count product by category
    public Long countProductByCategory(Long categoryId) {
        return spaCategoryRepo.countProductByCategory(categoryId);
    }

    // Chuyển đổi từ Category sang CategoryDTO
   private SpaCategoryDTO convertToDTO(SpaCategory category) {
        SpaCategoryDTO categoryDTO = new SpaCategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setImageUrl(category.getImageUrl());
        return categoryDTO;
    }

    // Chuyển đổi từ CategoryDTO sang Category
    private SpaCategory convertToEntity(SpaCategoryDTO categoryDTO) {
        SpaCategory category = new SpaCategory();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setImageUrl(categoryDTO.getImageUrl());
        return category;
    }
}
