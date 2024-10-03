package com.petspa.backend.repository;

import com.petspa.backend.entity.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {

    @Query("SELECT h FROM HotelBooking h WHERE h.account.id = :accountId")
    List<HotelBooking> findByAccountId(Long accountId);

    @Modifying
    @Transactional
    @Query("UPDATE HotelBooking h SET h.status = :status WHERE h.id = :id")
    int updateStatus(Long id, String status);

    @Modifying
    @Transactional
    @Query("UPDATE HotelBooking h SET h.rating = :rating, h.comment = :comment WHERE h.id = :id")
    int updateRatingAndComment(Long id, Integer rating, String comment);

    @Query("SELECT COUNT(h) FROM HotelBooking h WHERE h.account.id = :accountId")
    Long countByAccountId(Long accountId);

    @Modifying
    @Transactional
    @Query("DELETE FROM HotelBooking h WHERE h.id = :id")
    void deleteById(Long id);
}
