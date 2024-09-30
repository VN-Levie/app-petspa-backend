package com.petspa.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Thêm trường để đánh dấu xóa mềm
    @Column(nullable = false)
    private Boolean deleted = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PreRemove
    protected void onRemove() {
        this.deleted = true;
    }

    // Phương thức để thực hiện xóa mềm
    public void softDelete() {
        this.deleted = true;
        this.onUpdate(); // Cập nhật lại updatedAt
    }
}
