package com.petspa.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentOTP {

    @NotBlank
    @Size(min = 6, max = 100)
    private String orderId;

    @NotBlank
    private double amount;

    @NotBlank
    @Size(min = 6, max = 100)
    private String orderInfo;

    @NotBlank
    @Size(min = 6, max = 100)
    private String returnUrl;

    @NotBlank
    @Size(min = 6, max = 100)
    private String notifyUrl;

}
