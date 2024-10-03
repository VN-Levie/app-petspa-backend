package com.petspa.backend.repository;

import com.petspa.backend.dto.SpaProductDTO;
import com.petspa.backend.entity.Category;
import com.petspa.backend.entity.ShopOrder;
import com.petspa.backend.entity.SpaCategory;
import com.petspa.backend.entity.SpaProduct;

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
public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {

    Category findById(long id);

    @Modifying
    @Transactional
    @Query("UPDATE Category p SET p.deleted = true WHERE p.id = :id")
    boolean softDelete(Long id);

    // count
    @Query("SELECT COUNT(p) FROM ShopOrder p WHERE p.deleted = false")
    Long countAll();

    // countShopOrderByAccountId
    @Query("SELECT COUNT(p) FROM ShopOrder p WHERE p.account.id = :accountId AND p.deleted = false")
    Long countShopOrderByAccountId(Long accountId);

    // countShopOrderByAccount
    @Query("SELECT COUNT(p) FROM ShopOrder p WHERE p.account.id = :accountId AND p.deleted = false")
    Long countShopOrderByAccount(Long accountId);

    // getShopOrderByAccount
    @Query("SELECT p FROM ShopOrder p WHERE p.account.id = :accountId AND p.deleted = false")
    List<ShopOrder> getShopOrderByAccount(Long accountId);

    List<ShopOrder> findByAccountIdAndDeliveryAddressAndStatus(Long accountId, String deliveryAddress,
            String status);

    // shopOrderService.updateStatus(id, status);
    @Modifying
    @Query("UPDATE ShopOrder p SET p.status = :status WHERE p.id = :id")
    boolean updateStatus(Long id, String status);

    // shopOrderService.updatePaymentStatus(id, paymentStatus);
    @Modifying
    @Query("UPDATE ShopOrder p SET p.paymentStatus = :paymentStatus WHERE p.id = :id")
    boolean updatePaymentStatus(Long id, String paymentStatus);

    // shopOrderService.updateDeliveryDate(id, deliveryDate);
    @Modifying
    @Query("UPDATE ShopOrder p SET p.deliveryDate = :deliveryDate WHERE p.id = :id")
    boolean updateDeliveryDate(Long id, String deliveryDate);

    @Modifying
    @Query("UPDATE ShopOrder p SET p.status = 'Canceled by user' WHERE p.id = :id AND p.deleted = false AND p.status = 'Pending'")
    int cancelOrder(@Param("id") Long id);

}
