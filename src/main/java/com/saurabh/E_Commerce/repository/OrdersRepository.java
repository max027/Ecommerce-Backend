package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    boolean existsByOrderNumber(String orderNumber);
    @Query("""
        SELECT u FROM Orders  u JOIN
        OrderItems  oi ON u.ordersId=oi.orders.ordersId
        JOIN Products  p on p.productId=oi.products.productId
        WHERE p.vendors.id=:vendorId
        """)
    List<Orders> findAllByVendors(@Param("vendorId")long vendorId);

    @Query("""
        SELECT u FROM Orders  u JOIN
        OrderItems  oi ON u.ordersId=oi.orders.ordersId
        JOIN Products  p on p.productId=oi.products.productId
        WHERE p.vendors.id=:vendorId AND u.ordersId=:ordersId
        """)
    Optional<Orders> findByVendors(@Param("vendorId")long vendorId,@Param("orderId")long orderId);
}