package com.saurabh.E_Commerce.models;


import com.saurabh.E_Commerce.models.enums.ReferenceType;
import com.saurabh.E_Commerce.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "inventory_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTransaction extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long inventoryTransactionId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type",nullable = false)
    private TransactionType transactionType;

    @Column(name = "quantity_change",nullable = false)
    private int quantityChange;

    @Column(name = "quantity_before",nullable = false)
    private int quantityBefore;

    @Column(name = "quantity_after",nullable = false)
    private int quantityAfter;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type")
    private ReferenceType referenceType;

    @Column(name = "reference_id")
    private String referenceId;

    private  String notes;

}
