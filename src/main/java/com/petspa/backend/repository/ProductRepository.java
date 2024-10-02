package com.petspa.backend.repository;

import com.petspa.backend.entity.Product;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE "
            + "(:category IS NULL OR p.category.id = :category) AND "
            + "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Product> findAllProducts(String category, String search, Pageable pageable);

    //soft delete
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = :id")
    void softDelete(Long id);

}
