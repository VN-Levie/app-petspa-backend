package com.petspa.backend.dto;

import lombok.Data;

@Data
public class PetTypeDTO {

    private Long id;
    private String name;
    private String description;
    private String iconUrl; // Ảnh minh họa cho loại thú cưng
}
