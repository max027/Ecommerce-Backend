package com.saurabh.E_Commerce.models;

import com.saurabh.E_Commerce.models.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders",indexes = {
        @Index(name = "order_number_idx",columnList = "order_number"),
        @Index(name = "user_id_idx",columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long ordersId;

    @Column(name = "order_number",nullable = false,unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private Users users;

    @Enumerated(EnumType.STRING)
    private StatusEnum status=StatusEnum.PENDING;

    @Column(nullable = false,precision = 15,scale = 2)
    private BigDecimal subtotal;

    @Column(name = "discount_amount",precision = 15,scale = 2)
    private BigDecimal discountAmount=BigDecimal.ZERO;

    @Column(name = "tax_amount",precision = 15,scale = 2)
    private BigDecimal taxAmount=BigDecimal.ZERO;

    @Column(name = "shipping_amount",precision = 15,scale = 2)
    private BigDecimal shippingAmount=BigDecimal.ZERO;

    @Column(name = "total_amount",nullable = false,precision = 15,scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "coupon_code")
    private String couponCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_phone")
    private String customerPhone;

   @OneToMany(cascade =CascadeType.ALL,mappedBy = "orders",orphanRemoval = true)
   private List<OrderItems> orderItems=new ArrayList<>();


}
