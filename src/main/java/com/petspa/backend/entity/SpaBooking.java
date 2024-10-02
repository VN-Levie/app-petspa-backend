package com.petspa.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "spa_bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaBooking extends BaseEntity {

    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_CONFIRMED = "Confirmed";
    public static final String STATUS_CANCELLED = "Cancelled";
    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_REJECTED = "Rejected";
    public static final String STATUS_ON_GOING = "On Going";

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private SpaProduct service;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "booked_date", nullable = false)
    private LocalDateTime bookedDate;

    @Column(name = "used_date", nullable = false)
    private LocalDateTime usedDate;

    @Column(name = "used_time", nullable = false)
    private String usedTime;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

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
