package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.repository.InventoryTransactionRepository;
import com.saurabh.E_Commerce.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final ProductsRepository productsRepository;
}
