package com.petspa.backend.controller;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.CategoryDTO;
import com.petspa.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
       try {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched Cstegories successfully", categories);
        return ResponseEntity.ok(response);
       } catch (Exception e) {
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
       }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Added Category successfully", savedCategory);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        if (updatedCategory != null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Updated Category successfully", updatedCategory);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        } 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Deleted Category successfully", null);
        return ResponseEntity.ok(response);
    }
}
