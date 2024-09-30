package com.petspa.backend.repository;

import com.petspa.backend.entity.PetPhotoCategory;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PetPhotoCategoryRepository extends JpaRepository<PetPhotoCategory, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE PetPhotoCategory p SET p.deleted = true WHERE p.id = :id")
    void softDelete(Long id);
}
