package com.saurabh.E_Commerce.models;

import com.saurabh.E_Commerce.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import tools.jackson.databind.JsonNode;

import java.time.Instant;

@Entity
@Table(name = "payment_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long paymentTransactionId;


    @ManyToOne
    @JoinColumn(name = "payment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Payments payments;

    @Enumerated
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(nullable = false)
    private double amount;


    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    private String status;

//    @JdbcTypeCode(SqlTypes.JSON)
//    @Column(columnDefinition = "jsonb")
//    private JsonNode gatewayResponse;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;


}
