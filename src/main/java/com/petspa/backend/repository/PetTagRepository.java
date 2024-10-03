package com.petspa.backend.repository;

import com.petspa.backend.entity.PetTag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PetTagRepository extends JpaRepository<PetTag, Long> {
    // Find by name
    Optional<PetTag> findByName(String name);

    // Find by id
    PetTag findById(long id);

    @Modifying
    @Transactional
    @Query("UPDATE PetTag p SET p.deleted = true WHERE p.id = :id")
    void softDelete(@Param("id") Long id);
}
