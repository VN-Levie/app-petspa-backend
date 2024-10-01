package com.petspa.backend.repository;

import com.petspa.backend.dto.PetDTO;
import com.petspa.backend.entity.Pet;
import com.petspa.backend.entity.PetType;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByAccountId(Long accountId);

    @Query("SELECT p FROM Pet p WHERE "
            + "(:petType IS NULL OR p.petType.name = :petType) AND "
            + "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) "
            + "AND p.accountId = :accountId")
    Page<Pet> findAllPetsByAccount(String petType, String search, Long accountId, Pageable pageable);

    
    @Query("SELECT p FROM Pet p WHERE p.name = :#{#petDTO.name} AND p.height = :#{#petDTO.height} AND p.weight = :#{#petDTO.weight} AND p.accountId = :#{#petDTO.accountId} AND p.petType = :#{#petDTO.petType}")
    Optional<Pet> findPet(PetDTO petDTO);
    

    //findByAccountIdAndDeleted
    List<Pet> findByAccountIdAndDeleted(Long accountId, boolean deleted);
    

    //filter by account id
    @Query("SELECT p FROM Pet p WHERE p.accountId = :accountId")
    Page<Pet> findAllPetsByAccount(Long accountId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Pet p SET p.deleted = true WHERE p.id = :id")
    void softDelete(Long id);

    //countPetsByAccountId
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.accountId = :accountId AND p.deleted = false")
    Long countPetsByAccountId(Long accountId);

}
