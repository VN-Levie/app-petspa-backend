package com.petspa.backend.repository;

import com.petspa.backend.entity.PetTagOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetTagOrderRepository extends JpaRepository<PetTagOrder, Long> {
    List<PetTagOrder> findByAccountId(Long accountId);
}
