package com.petspa.backend.repository;

import com.petspa.backend.dto.SpaProductDTO;
import com.petspa.backend.entity.Category;
import com.petspa.backend.entity.SpaCategory;
import com.petspa.backend.entity.SpaProduct;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpaProductRepository extends JpaRepository<SpaProduct, Long> {
    Optional<Category> findByName(String name);

    Category findById(long id);

    @Modifying
    @Transactional
    @Query("UPDATE Category p SET p.deleted = true WHERE p.id = :id")
    void softDelete(Long id);

    //count
    @Query("SELECT COUNT(p) FROM SpaCategory p WHERE p.deleted = false")
    Long countAll();

    //countProductByCategory
    @Query("SELECT COUNT(p) FROM SpaProduct p WHERE p.category.id = :categoryId AND p.deleted = false")
    Long countProductByCategory(Long categoryId);

    //findAllProductsByCategory
    @Query("SELECT p FROM SpaProduct p WHERE p.category.id = :categoryId AND p.deleted = false")
    List<SpaProduct> findAllProductsByCategory(Long categoryId);
}
