package com.petspa.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "spa_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SpaCategory extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    // Liên kết với bảng SpaProduct
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SpaProduct> products;

    // Constructor với hai tham số (name, description)
    public SpaCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
