package com.petspa.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetTag extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    // Ảnh minh họa cho loại thú cưng
    @Column(name = "icon_url", nullable = true)
    private String iconUrl;

    @Column(nullable = false)
    private BigDecimal price;
}
