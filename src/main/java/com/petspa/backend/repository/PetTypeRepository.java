package com.petspa.backend.repository;

import com.petspa.backend.entity.PetType;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {
    //find by name
    Optional<PetType> findByName(String name);
    //find by id
    PetType findById(long id);

    @Modifying
    @Transactional
    @Query("UPDATE PetType p SET p.deleted = true WHERE p.id = :id")
    void softDelete(@Param("id") Long id);
}
