package com.petspa.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "shop_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrder extends BaseEntity {

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "delivery_date", nullable = false)
    private LocalDateTime deliveryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // Một map chứa sản phẩm và số lượng sản phẩm
    @ElementCollection
    @CollectionTable(name = "shop_order_products", joinColumns = @JoinColumn(name = "shop_order_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> productQuantities;

}
