package com.saurabh.E_Commerce.models;

import com.saurabh.E_Commerce.models.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.sql.SQLType;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payments extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long paymentId;


    @ManyToOne
    @JoinColumn(name = "orders_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Orders orders;

    @Column(name = "payment_gateway",nullable = false)
    private String paymentGateway;

    @Column(name = "payment_methods",nullable = false)
    private String paymentMethods;

    @Column(nullable = false)
    private  double amount;

    private String currency="INR";

    @Column(name = "gateway_payment_id")
    private String gatewayPaymentId;

    @Column(name = "gateway_order_id")
    private String gatewayOrderId;

    @Enumerated
    private StatusEnum status=StatusEnum.PENDING;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode gatewayResponse;

    @Column(name = "failure_test")
    private String failureTest;
}

