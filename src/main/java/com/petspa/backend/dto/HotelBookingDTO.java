package com.petspa.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.petspa.backend.entity.Account;
import com.petspa.backend.entity.Pet;

@Data
public class HotelBookingDTO {

    private long id;
   
    @NotNull(message = "Pet ID cannot be null")
    private long petId;

    
    @NotNull(message = "Account ID cannot be null")
    private long accountId;

    @NotNull(message = "Check In Date cannot be null")
    private LocalDateTime checkInDate;

    @NotNull(message = "Check Out Date cannot be null")
    private LocalDateTime checkOutDate;   

    @NotNull(message = "Total Price cannot be null")
    private BigDecimal totalPrice;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotBlank(message = "Pick Up Type cannot be blank")
    private String pickUpType; 

    @NotBlank(message = "Pick Up Address cannot be blank")
    private String pickUpAddress;

    @NotBlank(message = "Return Type cannot be blank")
    private String returnType; 

    @NotBlank(message = "Return Address cannot be blank")
    private String returnAddress;

    @NotBlank(message = "Payment Type cannot be blank")
    private String paymentType;

    
    private String note = "";

    
    private Integer rating = 0;

    
    private String comment = "";

   

}
