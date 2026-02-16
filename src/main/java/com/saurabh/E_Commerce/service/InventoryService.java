package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.ProductDtos.ProductDto;
import com.saurabh.E_Commerce.dto.inventory.AdjustStockDto;
import com.saurabh.E_Commerce.dto.inventory.TransactionDto;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.InventoryTransaction;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.enums.ReferenceType;
import com.saurabh.E_Commerce.models.enums.TransactionType;
import com.saurabh.E_Commerce.repository.InventoryTransactionRepository;
import com.saurabh.E_Commerce.repository.ProductsRepository;
import com.saurabh.E_Commerce.utils.DataMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final ProductsRepository productsRepository;

    public Page<ProductDto> getAllInventory(int page, int limit) {
        Pageable pageable= PageRequest.of(page,limit);
        return productsRepository.findAll(pageable).map(DataMapper::convertToProductDto);
    }


    private Products fetchProduct(long id){
        return productsRepository.findById(id).orElseThrow(
                ()->new ApiError("Product does not exist id:"+id, HttpStatus.NOT_FOUND.value())
        );
    }

    public ProductDto getProduct(long id) {
        Products products=fetchProduct(id);
        return DataMapper.convertToProductDto(products);
    }

    public void adjustStock(@Valid AdjustStockDto request) {
        Products products=fetchProduct(request.getProductId());

        InventoryTransaction transaction=new InventoryTransaction();
        transaction.setQuantityBefore(products.getStockQuantity());

        products.setStockQuantity(products.getStockQuantity()+ request.getQuantityChange());
        productsRepository.save(products);

        String type=request.getType().trim().toUpperCase();
        TransactionType transactionType=null;
        ReferenceType referenceType = null;
        switch (type) {
            case "RESTOCK" -> {
                referenceType=ReferenceType.RESTOCK;
                transactionType=TransactionType.IN;
            }
            case "RETURN" -> {
                referenceType=ReferenceType.RETURN;
                transactionType=TransactionType.IN;
            }
            case "MANUAL" -> {
                referenceType=ReferenceType.MANUAL;
                transactionType=TransactionType.ADJUSTMENT;
            }
            default -> throw new IllegalArgumentException("invalid reference type:" + type);
        };

        transaction.setQuantityChange(request.getQuantityChange());
        transaction.setQuantityAfter(products.getStockQuantity());
        transaction.setProducts(products);
        transaction.setReferenceType(referenceType);
        transaction.setTransactionType(transactionType);
        transaction.setNotes(request.getNotes());
        inventoryTransactionRepository.save(transaction);
    }

    public Page<TransactionDto> getTransaction(int page, int limit) {
        Pageable pageable= PageRequest.of(page,limit);
       return inventoryTransactionRepository.findAll(pageable).map(DataMapper::convertToTransactionDto);
    }
}
