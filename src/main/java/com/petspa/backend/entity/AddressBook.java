package com.petspa.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "address_books")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook extends BaseEntity {

    

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    //Họ tên, số điện thoại, email, ghi chú
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", nullable = true)
    private String email;

    // Liên kết với bảng Account, sử dụng ManyToOne vì một địa chỉ chỉ thuộc về một tài khoản
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
   
}

