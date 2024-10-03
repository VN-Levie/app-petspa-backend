package com.petspa.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PetTagDTO {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    // Ảnh minh họa cho loại thú cưng
    @NotBlank(message = "Icon URL cannot be blank")
    private String iconUrl;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;
}
