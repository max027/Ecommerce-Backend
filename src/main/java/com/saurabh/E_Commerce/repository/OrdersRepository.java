package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    boolean existsByOrderNumber(String orderNumber);
}