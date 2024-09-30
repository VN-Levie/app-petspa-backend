package com.petspa.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class PetDTO {

    private Long id;
    private String name;
    private String description;
    private Double height;
    private Double weight;
    private Long accountId;
    private String avatarUrl;
    private Long petTypeId; // Liên kết với PetType

    //isDeleted
    private boolean deleted;
}
