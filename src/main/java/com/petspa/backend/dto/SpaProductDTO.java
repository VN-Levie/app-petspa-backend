package com.petspa.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private Long category;
    private String description;
    private boolean deleted;
}
