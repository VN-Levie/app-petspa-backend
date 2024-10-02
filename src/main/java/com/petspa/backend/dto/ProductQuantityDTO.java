package com.petspa.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductQuantityDTO {

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;

    // toString
    @Override
    public String toString() {
        return "ProductQuantityDTO [productId=" + productId + ", quantity=" + quantity + "]";
    }
}
