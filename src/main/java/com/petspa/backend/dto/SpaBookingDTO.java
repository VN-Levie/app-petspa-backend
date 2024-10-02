package com.petspa.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SpaBookingDTO {

    
    private Long id;
    private Long serviceId;
    private Long petId;
    private Long accountId;
    private LocalDateTime bookedDate;
    private LocalDateTime usedDate;
    private String usedTime;
    private BigDecimal price;
    private String status;
    private String pickUpType;
    private String pickUpAddress;
    private String returnType;
    private String returnAddress;
    private String paymentType;
    private String note;
    private Integer rating;
    private String comment;

    // isDeleted
    private boolean deleted;

    // tosting
    @Override
    public String toString() {
        return "SpaBookingDTO [accountId=" + accountId + ", bookedDate=" + bookedDate + ", comment=" + comment
                + ", deleted=" + deleted + ", id=" + id + ", note=" + note + ", paymentType=" + paymentType + ", petId="
                + petId + ", pickUpType=" + pickUpType + ", price=" + price + ", rating=" + rating + ", returnType="
                + returnType + ", serviceId=" + serviceId + ", status=" + status + ", usedDate=" + usedDate
                + ", usedTime="
                + usedTime + "]";
    }

}