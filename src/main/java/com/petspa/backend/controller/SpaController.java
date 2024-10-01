package com.petspa.backend.controller;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.PetDTO;
import com.petspa.backend.dto.PetPhotoDTO;
import com.petspa.backend.dto.PetTypeDTO;
import com.petspa.backend.dto.SpaCategoryDTO;
import com.petspa.backend.dto.SpaProductDTO;
import com.petspa.backend.entity.PetPhoto;
import com.petspa.backend.dto.PetPhotoCategoryDTO;
import com.petspa.backend.service.PetService;
import com.petspa.backend.service.PetPhotoService;
import com.petspa.backend.service.PetTypeService;
import com.petspa.backend.service.SpaCaregoryService;
import com.petspa.backend.service.SpaProductService;
import com.petspa.backend.service.FileUploadService;
import com.petspa.backend.service.FileValidationService;
import com.petspa.backend.service.PetPhotoCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/spa")
public class SpaController {

    @Autowired
    private PetService petService;

    @Autowired
    private SpaCaregoryService spaCaregoryService;

    @Autowired
    private SpaProductService spaProductService;

    // #region Spa Category

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<SpaCategoryDTO> categories = spaCaregoryService.getAllCategories();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched categories successfully", categories);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody SpaCategoryDTO categoryDTO) {
        SpaCategoryDTO addedCategory = spaCaregoryService.addCategory(categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Added category successfully", addedCategory);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,
            @RequestBody SpaCategoryDTO categoryDTO) {
        SpaCategoryDTO updatedCategory = spaCaregoryService.updateCategory(id, categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Updated category successfully", updatedCategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        spaCaregoryService.deleteCategory(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Deleted category successfully", null);
        return ResponseEntity.ok(response);
    }

    //count all
    @GetMapping("/categories/count")
    public ResponseEntity<ApiResponse> countAllCategories() {
        Long count = spaCaregoryService.countAll();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Counted categories successfully", count);
        return ResponseEntity.ok(response);
    }

    // #endregion

    // #region Spa Product

    @GetMapping("/products")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<SpaProductDTO> products = spaProductService.getAll();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched products successfully", products);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody SpaProductDTO categoryDTO) {
        SpaProductDTO addedProduct = spaProductService.add(categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Added product successfully", addedProduct);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id,
            @RequestBody SpaProductDTO categoryDTO) {
        SpaProductDTO updatedProduct = spaProductService.update(id, categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Updated product successfully", updatedProduct);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        spaProductService.delete(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Deleted product successfully", null);
        return ResponseEntity.ok(response);
    }

    //count all
    @GetMapping("/products/count")
    public ResponseEntity<ApiResponse> countAllProducts() {
        Long count = spaProductService.countAll();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Counted products successfully", count);
        return ResponseEntity.ok(response);
    }

    //count all products by category id
    @GetMapping("/products/count/{categoryId}")
    public ResponseEntity<ApiResponse> countProductByCategory(@PathVariable Long categoryId) {
        Long count = spaCaregoryService.countProductByCategory(categoryId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Counted products by category successfully", count);
        return ResponseEntity.ok(response);
    }

    //get all products by category id
    @GetMapping("/products/{categoryId}")
    public ResponseEntity<ApiResponse> getAllProductsByCategory(@PathVariable Long categoryId) {
        List<SpaProductDTO> products = spaProductService.getAllByCategory(categoryId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched products by category successfully", products);
        return ResponseEntity.ok(response);
    }

    // #endregion
}
