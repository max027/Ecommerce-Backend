package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.OrdersDto.*;
import com.saurabh.E_Commerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponseDto> createOrder(
           @Valid @RequestBody CreateOrderDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Page<OrderResponseDto>>getAllOrders(@RequestParam int page,@RequestParam int limit){
        return ResponseEntity.ok(orderService.getAllOrders(page,limit));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponseDto>getOrder(@PathVariable long id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }


    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?>cancelOrder(@Valid @RequestBody CancelOrderDto request,@PathVariable long id){
        orderService.cancelOrder(request,id);
        return ResponseEntity.ok().body("order canceled");
    }
    @PostMapping("/{id}/review")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?>createReview(@Valid @RequestBody ReviewDto request, @PathVariable long id){
        orderService.createReview(request,id);
        return ResponseEntity.ok().body("review added");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>>getOrders(@RequestParam int page,@RequestParam int limit){
        return ResponseEntity.ok(orderService.getAllOrders(page,limit));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>updateOrderStatus(@Valid @RequestBody UpdateStatusDto request,@PathVariable long id){
        orderService.updateOrderStatus(request,id);
        return ResponseEntity.ok().body("order status updated");
    }

    @GetMapping("/{id}/timeline")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderTimeLineDto>>getTileLine(@PathVariable long id){
        return ResponseEntity.ok().body(orderService.getTimelineHistory(id));
    }

}
