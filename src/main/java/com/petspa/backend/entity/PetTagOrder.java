package com.petspa.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet_tag_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetTagOrder extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_tag_id", nullable = false)
    private PetTag petTag;

    @Column(name = "text_front", nullable = false)
    private String textFront;

    @Column(name = "text_back", nullable = false)
    private String textBack;

    @Column(name = "num", nullable = false)
    private int num;

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;

    @Column(name = "receiver_email", nullable = false)
    private String receiverEmail;

    @Column(name = "receiver_address_id", nullable = false)
    private Long receiverAddressId;
}
