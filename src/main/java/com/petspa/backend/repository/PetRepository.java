package com.petspa.backend.repository;

import com.petspa.backend.entity.Pet;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByAccountId(Long accountId);

    @Query("SELECT p FROM Pet p WHERE "
            + "(:petType IS NULL OR p.petType.name = :petType) AND "
            + "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) "
            + "AND p.accountId = :accountId")
    Page<Pet> findAllPetsByAccount(String petType, String search, Long accountId, Pageable pageable);

    //filter by account id
    @Query("SELECT p FROM Pet p WHERE p.accountId = :accountId")
    Page<Pet> findAllPetsByAccount(Long accountId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Pet p SET p.deleted = true WHERE p.id = :id")
    void softDelete(Long id);

}
