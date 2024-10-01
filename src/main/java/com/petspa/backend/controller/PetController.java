package com.petspa.backend.controller;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.PetDTO;
import com.petspa.backend.dto.PetPhotoDTO;
import com.petspa.backend.dto.PetTypeDTO;
import com.petspa.backend.entity.PetPhoto;
import com.petspa.backend.dto.PetPhotoCategoryDTO;
import com.petspa.backend.service.PetService;
import com.petspa.backend.service.PetPhotoService;
import com.petspa.backend.service.PetTypeService;
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
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private PetPhotoService petPhotoService;

    @Autowired
    private PetTypeService petTypeService;

    @Autowired
    private PetPhotoCategoryService petPhotoCategoryService;

    @Autowired
    private FileValidationService fileValidationService;

    @Autowired
    private FileUploadService fileUploadService;

   

    // Lấy tất cả thú cưng
    @GetMapping // TODO: CHuyển sang cho admin
    public ResponseEntity<ApiResponse> getAllPets() {
        List<PetDTO> pets = petService.getAllPets();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of pets", pets);
        return ResponseEntity.ok(response);
    }

    // lay61 tat61 ca3 pet theo account id
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse> getAllPetsByAccount(@PathVariable Long accountId) {
        List<PetDTO> pets = petService.getAllPetsByAccountId(accountId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of pets", pets);
        return ResponseEntity.ok(response);
    }

    // Lấy thú cưng theo ID
    @GetMapping("/{accountId}/{petId}")
    public ResponseEntity<ApiResponse> getPetById(
            @PathVariable Long petId,
            @PathVariable Long accountId) {

        Optional<PetDTO> pet = Optional.ofNullable(petService.getPetByIdAndAccountId(petId, accountId));

        if (pet.isEmpty()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Pet not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet details", pet.get());
        return ResponseEntity.ok(response);
    }

    //đếm số lượng pet theo account id
    @GetMapping("/count/{accountId}")
    public ResponseEntity<ApiResponse> countPetsByAccountId(@PathVariable Long accountId) {
        Long count = petService.countPetsByAccountId(accountId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Number of pets", count);
        return ResponseEntity.ok(response);
    }

    // Thêm thú cưng mới
    @PostMapping(value = "/add", consumes = { "multipart/form-data" })
    public ResponseEntity<ApiResponse> addPet(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("height") Double height,
            @RequestParam("weight") Double weight,
            @RequestParam("accountId") Long accountId,
            @RequestParam("petTypeId") Long petTypeId,
            @RequestParam("avatar") MultipartFile avatarFile) {

        PetDTO petDTO = new PetDTO();
        petDTO.setId((long) 0);
        petDTO.setName(name);
        petDTO.setDescription(description);
        petDTO.setHeight(height);
        petDTO.setWeight(weight);
        petDTO.setAccountId(accountId);
        petDTO.setPetTypeId(petTypeId);
        try {
            // kiểm tra xem pet type có tồn tại không
            if (petTypeService.getPetTypeById(petDTO.getPetTypeId()) == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Pet type not found", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            try {
                // check trùng thông tin
                PetDTO pet =petService.findPet(petDTO);
                if (pet != null && !pet.isDeleted()) {
                    ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Pet already exists", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, e.getMessage(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Upload avatar file
            String avatarUrl = null;
            if (avatarFile != null && !avatarFile.isEmpty()) {
                // kiểm tra loại file ảnh
                if (!fileValidationService.isImageValid(avatarFile)) {
                    ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                            "Please upload a valid image file (jpg, jpeg, png, gif, bmp, webp) with size less than 50MB"
                                    + avatarFile.getContentType(),
                            null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                avatarUrl = fileUploadService.uploadFile(avatarFile, "pets"); // Hàm xử lý upload ảnh
            }
            System.out.println("avatarUrl: " + avatarUrl);
            petDTO.setAvatarUrl(avatarUrl);

            PetDTO createdPet = petService.addPet(petDTO);
            List<PetPhotoDTO> photos = petPhotoService.getAllPhotosByPetId(createdPet.getId());
            PetPhotoCategoryDTO avCategoryDTO = petPhotoCategoryService.getPhotoCategoryById((long) 1);
            if(avCategoryDTO == null){
                PetPhotoCategoryDTO petPhotoCategoryDTO = new PetPhotoCategoryDTO();
                petPhotoCategoryDTO.setId((long) 1);
                petPhotoCategoryDTO.setName("Avatar");
                petPhotoCategoryDTO.setDescription("Avatar photo category");
                petPhotoCategoryService.addPhotoCategory(petPhotoCategoryDTO);
            }
            if(photos.size() == 0){
                PetPhotoDTO petPhotoDTO = new PetPhotoDTO();
                petPhotoDTO.setPetId(createdPet.getId());
                petPhotoDTO.setPhotoUrl(createdPet.getAvatarUrl());
                petPhotoDTO.setPhotoCategoryId((long) 1);
                petPhotoDTO.setUploadedBy(petDTO.getAccountId());
                petPhotoService.addPhoto(petPhotoDTO);
            }

            ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Pet added successfully", createdPet);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // po

    // Cập nhật thú cưng
    @PutMapping(value = "/{accountId}/{petId}", consumes = { "multipart/form-data" })
    public ResponseEntity<ApiResponse> updatePet(
            @PathVariable Long accountId,
            @PathVariable Long petId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("height") Double height,
            @RequestParam("weight") Double weight,
            @RequestParam("petTypeId") Long petTypeId,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile) {

        // Kiểm tra thú cưng có tồn tại không
        if (petService.getPetById(petId) == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Pet not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Kiểm tra loại thú cưng có tồn tại không
        if (petTypeService.getPetTypeById(petTypeId) == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Pet type not found", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // kiểm tra deleted = false
        if (petService.getPetById(petId).isDeleted()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Pet not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        //kiểm tra account id
        if (!petService.getPetById(petId).getAccountId().equals(accountId)) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_FORBIDDEN, "You are not allowed to update this pet", null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        PetDTO petDTO = new PetDTO();
        petDTO.setId(petId);
        petDTO.setName(name);
        petDTO.setDescription(description);
        petDTO.setHeight(height);
        petDTO.setWeight(weight);
        petDTO.setPetTypeId(petTypeId);

        // Upload avatar file (nếu có)
        String avatarUrl = null;
        if (avatarFile != null && !avatarFile.isEmpty()) {
            // kiểm tra loại file ảnh
            if (!fileValidationService.isImageValid(avatarFile)) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                        "Please upload a valid image file (jpg, jpeg, png, gif, bmp, webp) with size less than 50MB",
                        null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            avatarUrl = fileUploadService.uploadFile(avatarFile, "pets"); // Hàm xử lý upload ảnh
        }
        if(avatarUrl != null){
             petDTO.setAvatarUrl(avatarUrl);
        } else {
            petDTO.setAvatarUrl(petService.getPetById(petId).getAvatarUrl());
        }
       

        PetDTO updatedPet = petService.updatePet(petId, petDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet updated successfully", updatedPet);
        return ResponseEntity.ok(response);
    }

    // Xóa thú cưng
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    // Lấy tất cả loại thú cưng
    @GetMapping("/types")
    public ResponseEntity<ApiResponse> getAllPetTypes() {
        List<PetTypeDTO> petTypes = petTypeService.getAllPetTypes();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of pet types", petTypes);
        return ResponseEntity.ok(response);
    }

    // thêm loại thú cưng mới

    @PostMapping(value = "/types", consumes = { "multipart/form-data" }) // TODO: Chuyển sang cho admin
    public ResponseEntity<ApiResponse> addPetType(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "icon", required = false) MultipartFile iconFile) {

        try {
            // Kiểm tra xem loại thú cưng có tồn tại hay không
            if (petTypeService.getPetTypeByName(name) != null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Pet type already exists", null);
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

                iconUrl = fileUploadService.uploadFile(iconFile, "pet-types"); // Hàm xử lý upload ảnh
            }

            PetTypeDTO petTypeDTO = new PetTypeDTO();
            petTypeDTO.setName(name);
            petTypeDTO.setDescription(description);
            petTypeDTO.setIconUrl(iconUrl);

            PetTypeDTO createdPetType = petTypeService.addPetType(petTypeDTO);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Pet type added successfully",
                    createdPetType);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Cập nhật loại thú cưng
    @PutMapping(value = "/types/{id}", consumes = { "multipart/form-data" }) // TODO: Chuyển sang cho admin
    public ResponseEntity<ApiResponse> updatePetType(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "icon", required = false) MultipartFile iconFile) {

        // Kiểm tra loại thú cưng có tồn tại không
        if (petTypeService.getPetTypeById(id) == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Pet type not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Upload icon file (nếu có)
        String iconUrl = null;
        if (iconFile != null && !iconFile.isEmpty()) {
            iconUrl = fileUploadService.uploadFile(iconFile, "pet-type");
        }

        PetTypeDTO petTypeDTO = new PetTypeDTO();
        petTypeDTO.setName(name);
        petTypeDTO.setDescription(description);
        petTypeDTO.setIconUrl(iconUrl);

        PetTypeDTO updatedPetType = petTypeService.updatePetType(id, petTypeDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet type updated successfully", updatedPetType);
        return ResponseEntity.ok(response);
    }

    // Xóa loại thú cưng
    @DeleteMapping("/types/{id}") // TODO: Chuyển sang cho admin
    public ResponseEntity<ApiResponse> deletePetType(@PathVariable Long id) {
        // kiểm tra xem loại thú cưng có tồn tại không
        if (petTypeService.getPetTypeById(id) == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Pet type not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        petTypeService.deletePetType(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet type deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    // Lấy tất cả loại ảnh thú cưng
    @GetMapping("/photo-categories")
    public ResponseEntity<ApiResponse> getAllPhotoCategories() {
        List<PetPhotoCategoryDTO> photoCategories = petPhotoCategoryService.getAllPhotoCategories();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of pet photo categories", photoCategories);
        return ResponseEntity.ok(response);
    }

    // Thêm loại ảnh thú cưng mới
    @PostMapping("/photo-categories") // TODO: Chuyển sang cho admin
    public ResponseEntity<ApiResponse> addPhotoCategory(@RequestBody PetPhotoCategoryDTO petPhotoCategoryDTO) {
        PetPhotoCategoryDTO createdPhotoCategory = petPhotoCategoryService.addPhotoCategory(petPhotoCategoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Pet photo category added successfully",
                createdPhotoCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Cập nhật loại ảnh thú cưng
    @PutMapping("/photo-categories/{id}") // TODO: Chuyển sang cho admin
    public ResponseEntity<ApiResponse> updatePhotoCategory(@PathVariable Long id,
            @RequestBody PetPhotoCategoryDTO petPhotoCategoryDTO) {
        PetPhotoCategoryDTO updatedPhotoCategory = petPhotoCategoryService.updatePhotoCategory(id, petPhotoCategoryDTO);
        if (updatedPhotoCategory == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Pet photo category not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet photo category updated successfully",
                updatedPhotoCategory);
        return ResponseEntity.ok(response);
    }

    // Xóa loại ảnh thú cưng
    @DeleteMapping("/photo-categories/{id}") // TODO: Chuyển sang cho admin
    public ResponseEntity<ApiResponse> deletePhotoCategory(@PathVariable Long id) {
        petPhotoCategoryService.deletePhotoCategory(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet photo category deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    // Lấy tất cả ảnh của thú cưng
    @GetMapping("/{petId}/photos")
    public ResponseEntity<ApiResponse> getAllPhotosByPetId(@PathVariable Long petId) {
        List<PetPhotoDTO> photos = petPhotoService.getAllPhotosByPetId(petId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of pet photos", photos);
        return ResponseEntity.ok(response);
    }

    // Thêm ảnh cho thú cưng
    @PostMapping("/{petId}/photos")
    public ResponseEntity<ApiResponse> addPhotoForPet(@PathVariable Long petId, @RequestBody PetPhotoDTO petPhotoDTO) {
        petPhotoDTO.setPetId(petId);
        PetPhotoDTO createdPhoto = petPhotoService.addPhoto(petPhotoDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Pet photo added successfully",
                createdPhoto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Xóa ảnh của thú cưng
    @DeleteMapping("/photos/{photoId}")
    public ResponseEntity<ApiResponse> deletePhoto(@PathVariable Long photoId) {
        petPhotoService.deletePhoto(photoId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet photo deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
