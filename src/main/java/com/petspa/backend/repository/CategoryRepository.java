package com.petspa.backend.repository;

import com.petspa.backend.entity.Category;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Category findById(long id);

    @Modifying
    @Transactional
    @Query("UPDATE Category p SET p.deleted = true WHERE p.id = :id")
    void softDelete(Long id);
}
