package com.petspa.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetTagOrderDTO {

    private Long id;

    @NotNull(message = "Account ID cannot be null")
    private Long accountId;

    @NotNull(message = "Pet Tag ID cannot be null")
    private Long petTagId;

    @NotBlank(message = "Text Front cannot be blank")
    private String textFront;

    @NotBlank(message = "Text Back cannot be blank")
    private String textBack;

    @NotNull(message = "Quantity cannot be null")
    private Integer num;

    @NotBlank(message = "Full Address cannot be blank")
    private String fullAddress;

    @NotBlank(message = "Receiver Name cannot be blank")
    private String receiverName;

    @NotBlank(message = "Receiver Phone cannot be blank")
    private String receiverPhone;

    @NotBlank(message = "Receiver Email cannot be blank")
    private String receiverEmail;

    @NotNull(message = "Receiver Address ID cannot be null")
    private Long receiverAddressId;
}
