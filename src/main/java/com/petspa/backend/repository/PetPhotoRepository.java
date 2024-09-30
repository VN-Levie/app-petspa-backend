package com.petspa.backend.repository;

import com.petspa.backend.entity.PetPhoto;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PetPhotoRepository extends JpaRepository<PetPhoto, Long> {

    List<PetPhoto> findByPetId(Long petId);

    List<PetPhoto> findByPhotoCategoryId(Long photoCategoryId);

    @Modifying
    @Transactional
    @Query("UPDATE PetPhoto p SET p.deleted = true WHERE p.id = :id")
    void softDelete(Long id);
}
