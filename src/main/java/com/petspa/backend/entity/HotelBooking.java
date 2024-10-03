package com.petspa.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hotel_bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelBooking extends BaseEntity {

    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_CONFIRMED = "Confirmed";
    public static final String STATUS_CANCELLED = "Cancelled";
    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_REJECTED = "Rejected";
    public static final String STATUS_ON_GOING = "On Going";

    

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDateTime checkOutDate;   

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "pick_up_type", nullable = false)
    private String pickUpType; 

    @Column(name = "pick_up_address", nullable = false)
    private String pickUpAddress;

    @Column(name = "return_type", nullable = false)
    private String returnType; 

    @Column(name = "return_address", nullable = false)
    private String returnAddress;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "note", nullable = true)
    private String note;

    @Column(name = "rating", nullable = true)
    private Integer rating;

    @Column(name = "comment", nullable = true)
    private String comment;
}
