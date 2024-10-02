package com.petspa.backend.dto;

import lombok.Data;

@Data
public class AddressBookDTO {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Long accountId;
    ////Họ tên, số điện thoại, email, ghi chú
    private String fullName;
    private String phoneNumber;
    private String email;

    // toString
    @Override
    public String toString() {
        return "AddressBookDTO [id=" + id + ", street=" + street + ", city=" + city 
            + ", state=" + state + ", postalCode=" + postalCode 
            + ", country=" + country + ", accountId=" + accountId + "]";
    }
}
