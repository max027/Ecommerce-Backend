package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
}