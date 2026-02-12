package com.saurabh.E_Commerce.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long orderItemsId;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @Column(name = "product_name",nullable = false)
    private String productName;

    @Column(name = "product_sku",nullable = false)
    private String productSku;

    private int quantity;

    @Column(name = "unit_price", nullable = false,precision = 15,scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false,precision = 15,scale = 2)
    private BigDecimal totalPrice;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

}
