package com.petspa.backend.service;

import com.petspa.backend.dto.CategoryDTO;
import com.petspa.backend.dto.SpaProductDTO;
import com.petspa.backend.dto.SpaProductDTO;
import com.petspa.backend.entity.Category;
import com.petspa.backend.entity.SpaCategory;
import com.petspa.backend.entity.SpaProduct;
import com.petspa.backend.entity.SpaProduct;
import com.petspa.backend.repository.CategoryRepository;
import com.petspa.backend.repository.SpaCategoryRepository;
import com.petspa.backend.repository.SpaProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpaProductService {

    @Autowired
    private SpaProductRepository spaProductRepo;

    @Autowired
    private SpaCategoryRepository categoryRepository;

    // Lấy tất cả sản phẩm
    public List<SpaProductDTO> getAll() {
        return spaProductRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm sản phẩm mới
    public SpaProductDTO add(SpaProductDTO categoryDTO) {
        SpaProduct category = convertToEntity(categoryDTO);
        category = spaProductRepo.save(category);
        return convertToDTO(category);
    }

    // Cập nhật sản phẩm
    public SpaProductDTO update(Long id, SpaProductDTO categoryDTO) {
        Optional<SpaProduct> existingCategory = spaProductRepo.findById(id);
        if (existingCategory.isPresent()) {
            SpaProduct category = existingCategory.get();
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            category = spaProductRepo.save(category);
            return convertToDTO(category);
        }
        return null;
    }

    

    // Xóa sản phẩm
    public void delete(Long id) {
        spaProductRepo.softDelete(id);
    }

    //count all
    public Long countAll() {
        return spaProductRepo.countAll();
    }

    // Lấy sản phẩm theo ID
    public SpaProductDTO getById(Long id) {
        return spaProductRepo.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    //lấy tât cả sản phẩm theo danh mục
    public List<SpaProductDTO> getAllByCategory(Long categoryId) {
        return spaProductRepo.findAllProductsByCategory(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Chuyển đổi từ Category sang CategoryDTO
   private SpaProductDTO convertToDTO(SpaProduct category) {
        SpaProductDTO categoryDTO = new SpaProductDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setPrice(category.getPrice());
        categoryDTO.setCategory(category.getCategory().getId());
        categoryDTO.setImageUrl(category.getImageUrl());
        categoryDTO.setDeleted(category.isDeleted());
        return categoryDTO;
    }

    // Chuyển đổi từ CategoryDTO sang Category
    private SpaProduct convertToEntity(SpaProductDTO categoryDTO) {
        SpaProduct category = new SpaProduct();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setPrice(categoryDTO.getPrice());
        Optional<SpaCategory> spaCategory = categoryRepository.findById(categoryDTO.getCategory());
        spaCategory.ifPresent(category::setCategory);
        category.setImageUrl(categoryDTO.getImageUrl());
        category.setDeleted(categoryDTO.isDeleted());

        return category;
    }
}
