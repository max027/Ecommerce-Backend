package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.ProductDtos.ProductDto;
import com.saurabh.E_Commerce.dto.inventory.AdjustStockDto;
import com.saurabh.E_Commerce.dto.inventory.TransactionDto;
import com.saurabh.E_Commerce.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private  final InventoryService inventoryService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('VIEW_INVENTORY')")
    public ResponseEntity<Page<ProductDto>>getAllInventory(@RequestParam int page, @RequestParam int limit){
        return ResponseEntity.ok(inventoryService.getAllInventory(page,limit));
    }


    @GetMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('VIEW_INVENTORY')")
    public ResponseEntity<ProductDto>getProduct(@PathVariable long id){
        return ResponseEntity.ok(inventoryService.getProduct(id));
    }

    @PostMapping("/adjust")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ADJUST_STOCK')")
    public ResponseEntity<?>adjustStock(@Valid @RequestBody AdjustStockDto request){
        inventoryService.adjustStock(request);
        return ResponseEntity.ok("stock adjusted");
    }

    @GetMapping("/transaction")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('VIEW_INVENTORY')")
    public ResponseEntity<Page<TransactionDto>>getTransaction(@RequestParam int page, @RequestParam int limit){
        return ResponseEntity.ok(inventoryService.getTransaction(page,limit));
    }
}
