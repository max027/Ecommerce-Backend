package com.saurabh.E_Commerce.models;

import com.saurabh.E_Commerce.models.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long ordersId;

    @Column(name = "order_number",nullable = false)
    private int orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Enumerated(EnumType.STRING)
    private StatusEnum status=StatusEnum.PENDING;

    @Column(nullable = false)
    private double subtotal;

    @Column(name = "discount_amount")
    private double discountAmount=0.0d;

    @Column(name = "tax_amount")
    private double taxAmount=0.0d;

    @Column(name = "shipping_amount")
    private double shippingAmount=0.0d;

    @Column(name = "total_amount",nullable = false)
    private double totalAmount;

    @Column(name = "coupon_code")
    private String couponCode;

    @ManyToOne
    @JoinColumn(name = "shipping_address")
    private Address shippingAddress;


    @ManyToOne
    @JoinColumn(name = "billing_address")
    private Address billingAddress;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_phone")
    private String customerPhone;

   @OneToMany(cascade ={CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "orders")
   private List<OrderItems> orderItems;


}
