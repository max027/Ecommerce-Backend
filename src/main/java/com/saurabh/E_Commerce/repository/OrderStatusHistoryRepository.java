package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    Optional<OrderStatusHistory> findByOrdersOrdersId(long id);
    List<OrderStatusHistory> findAllByOrdersOrdersId(long id);
}