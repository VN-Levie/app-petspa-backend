package com.petspa.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "Account")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", insertable = false, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at"))
})
public class Account extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "roles")
    private String roles; // Example: "ROLE_USER,ROLE_ADMIN"

    @Column(name = "name")
    private String name;

    // Liên kết với bảng AddressBook, sử dụng OneToMany vì một tài khoản có thể có nhiều địa chỉ
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AddressBook> addressBooks;

  
}
