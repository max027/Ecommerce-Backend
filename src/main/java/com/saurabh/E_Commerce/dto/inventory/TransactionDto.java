package com.saurabh.E_Commerce.dto.inventory;

import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.enums.ReferenceType;
import com.saurabh.E_Commerce.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private long inventoryTransactionId;

    private long productId;

    private String transactionType;

    private int quantityChange;

    private int quantityBefore;

    private int quantityAfter;

    private String referenceType;

    private long referenceId;

    private  String notes;

}
