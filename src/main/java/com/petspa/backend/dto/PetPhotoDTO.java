package com.petspa.backend.dto;

import lombok.Data;

@Data
public class PetPhotoDTO {

    private Long id;
    private Long petId; // Liên kết thú cưng
    private String photoUrl;
    private Long photoCategoryId; // Liên kết loại ảnh
    private Long uploadedBy; // Người tải ảnh lên
}
