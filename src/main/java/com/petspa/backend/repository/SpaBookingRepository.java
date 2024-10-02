package com.petspa.backend.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.petspa.backend.entity.Account;
import com.petspa.backend.entity.SpaBooking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpaBookingRepository extends JpaRepository<SpaBooking, Long> {
    Optional<SpaBooking> findByAccountId(Long accountId);

    List<SpaBooking> findByAccountIdAndDeleted(Long accountId, boolean deleted);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.deleted = true WHERE a.id = :id")
    void softDelete(Long id);
}
