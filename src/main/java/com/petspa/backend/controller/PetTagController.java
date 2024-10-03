package com.petspa.backend.controller;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.PetTagDTO;
import com.petspa.backend.service.PetTagService;
import com.petspa.backend.service.FileUploadService;
import com.petspa.backend.service.FileValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pet-tags")
public class PetTagController {

    @Autowired
    private PetTagService petTagService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private FileValidationService fileValidationService;

    // Lấy tất cả bảng tên thú cưng
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPetTags() {
        List<PetTagDTO> petTags = petTagService.getAllPetTags();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of pet tags", petTags);
        return ResponseEntity.ok(response);
    }

    // Lấy bảng tên thú cưng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPetTagById(@PathVariable Long id) {
        PetTagDTO petTag = petTagService.getPetTagById(id);

        if (petTag == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Pet tag not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet tag details", petTag);
        return ResponseEntity.ok(response);
    }

    // Thêm bảng tên thú cưng mới
    @PostMapping(value = "/add", consumes = { "multipart/form-data" })
    public ResponseEntity<ApiResponse> addPetTag(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "icon", required = false) MultipartFile iconFile) {

        try {
            // Kiểm tra xem bảng tên thú cưng có tồn tại hay không
            if (petTagService.getPetTagByName(name) != null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Pet tag already exists", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Upload icon file (nếu có)
            String iconUrl = null;
            if (iconFile != null && !iconFile.isEmpty()) {

                // kiểm tra loại file ảnh
                if (!fileValidationService.isImageValid(iconFile)) {
                    ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                            "Please upload a valid image file (jpg, jpeg, png, gif, bmp, webp) with size less than 50MB",
                            null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                iconUrl = fileUploadService.uploadFile(iconFile, "pet-tags");
            }

            PetTagDTO petTagDTO = new PetTagDTO();
            petTagDTO.setName(name);
            petTagDTO.setDescription(description);
            petTagDTO.setIconUrl(iconUrl);

            PetTagDTO createdPetTag = petTagService.addPetTag(petTagDTO);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Pet tag added successfully",
                    createdPetTag);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Cập nhật bảng tên thú cưng
    @PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<ApiResponse> updatePetTag(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "icon", required = false) MultipartFile iconFile) {

        // Kiểm tra bảng tên thú cưng có tồn tại không
        if (petTagService.getPetTagById(id) == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Pet tag not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Upload icon file (nếu có)
        String iconUrl = null;
        if (iconFile != null && !iconFile.isEmpty()) {
            iconUrl = fileUploadService.uploadFile(iconFile, "pet-tags");
        }

        PetTagDTO petTagDTO = new PetTagDTO();
        petTagDTO.setName(name);
        petTagDTO.setDescription(description);
        petTagDTO.setIconUrl(iconUrl);

        PetTagDTO updatedPetTag = petTagService.updatePetTag(id, petTagDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet tag updated successfully", updatedPetTag);
        return ResponseEntity.ok(response);
    }

    // Xóa bảng tên thú cưng
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePetTag(@PathVariable Long id) {
        // kiểm tra xem bảng tên thú cưng có tồn tại không
        if (petTagService.getPetTagById(id) == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Pet tag not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        petTagService.deletePetTag(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet tag deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
