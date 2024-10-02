package com.petspa.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShopOrderDTO {

    private Long id;

    @NotNull(message = "Account ID cannot be null")
    private Long accountId;

    @NotNull(message = "Total Price cannot be null")
    private BigDecimal totalPrice;

    @NotBlank(message = "Status cannot be blank")
    @Size(max = 20, message = "Status should not exceed 20 characters")
    private String status;

    @NotBlank(message = "Payment Type cannot be blank")
    @Size(max = 20, message = "Payment Type should not exceed 20 characters")
    private String paymentType;

    @NotBlank(message = "Payment Status cannot be blank")
    @Size(max = 20, message = "Payment Status should not exceed 20 characters")
    private String paymentStatus;

    @NotBlank(message = "Delivery Address cannot be blank")
    @Size(max = 255, message = "Delivery Address should not exceed 255 characters")
    private String deliveryAddress;

    @NotNull(message = "Delivery Date cannot be null")
    private LocalDateTime deliveryDate;

    @NotNull(message = "Product Quantities cannot be null")
    private List<ProductQuantityDTO> productQuantities; // Danh sách ProductQuantityDTO

    // toString
    @Override
    public String toString() {
        return "ShopOrderDTO [id=" + id + ", accountId=" + accountId
                + ", totalPrice=" + totalPrice + ", status=" + status + ", paymentType=" + paymentType
                + ", paymentStatus=" + paymentStatus + ", deliveryAddress=" + deliveryAddress
                + ", deliveryDate=" + deliveryDate + ", productQuantities=" + productQuantities + "]";
    }
}