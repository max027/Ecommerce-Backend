package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.OrdersDto.*;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.*;
import com.saurabh.E_Commerce.models.enums.ReferenceType;
import com.saurabh.E_Commerce.models.enums.StatusEnum;
import com.saurabh.E_Commerce.models.enums.TransactionType;
import com.saurabh.E_Commerce.repository.*;
import com.saurabh.E_Commerce.security.AuthUtils;
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

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final AddressRepository addressRepository;
    private final ProductsRepository productsRepository;
    private final AuthUtils authUtils;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ReviewRepository reviewRepository;


    @Transactional
    public OrderResponseDto createOrder(@Valid  CreateOrderDto request){
        Users users=authUtils.getCurrentUser();
        Address shipping=addressRepository.findById(request.getShippingAddress()).orElseThrow(
                ()->new ApiError("Shipping address not found", HttpStatus.NOT_FOUND.value())
        );
        Address billing=addressRepository.findById(request.getBillingAddress()).orElseThrow(
                ()->new ApiError("Shipping address not found", HttpStatus.NOT_FOUND.value())
        );

        Orders orders=new Orders();
        orders.setUsers(users);
        orders.setBillingAddress(billing);
        orders.setShippingAddress(shipping);
        orders.setCouponCode(request.getCouponCode());
        orders.setCustomerEmail(users.getEmail());
        orders.setCustomerPhone(users.getPhone());
        orders.setOrderNumber(generateOrderNumber());

        BigDecimal subtotal=BigDecimal.ZERO;
        for(ItemsDto items: request.getItems()){
            Products products=productsRepository.findById(items.getProductId()).orElseThrow(
                    ()->new ApiError("product not found id:"+items.getProductId(),HttpStatus.NOT_FOUND.value())
            );
            //insert transaction
            InventoryTransaction transaction=new InventoryTransaction();
            transaction.setProducts(products);
            transaction.setTransactionType(TransactionType.OUT);
            transaction.setQuantityChange(items.getQuantity());
            transaction.setQuantityBefore(products.getStockQuantity());
            transaction.setQuantityAfter(products.getStockQuantity()-items.getQuantity());
            transaction.setReferenceType(ReferenceType.ORDER);
            transaction.setReferenceId(orders.getOrdersId());
            inventoryTransactionRepository.save(transaction);

            //reduce stock
            products.setStockQuantity(products.getStockQuantity()-items.getQuantity());
            BigDecimal itemPrice=products.getPrice();
            BigDecimal quantity=BigDecimal.valueOf(items.getQuantity());

            BigDecimal itemTotal=itemPrice.multiply(quantity);
            subtotal=subtotal.add(itemTotal);

            OrderItems orderItems=new OrderItems();
            orderItems.setOrders(orders);
            orderItems.setProducts(products);
            orderItems.setQuantity(items.getQuantity());
            orderItems.setUnitPrice(itemPrice);
            orderItems.setTotalPrice(itemTotal);
            orderItems.setProductName(products.getName());
            orderItems.setProductSku(products.getSku());

            orders.getOrderItems().add(orderItems);
        }
        orders.setSubtotal(subtotal);

        BigDecimal tax=subtotal.multiply(new BigDecimal("0.18"));
        orders.setTaxAmount(tax);

        BigDecimal shippingAmount=new BigDecimal("50.00");
        orders.setShippingAmount(shippingAmount);

        BigDecimal discount=BigDecimal.ZERO;
        orders.setDiscountAmount(discount);
        BigDecimal total=subtotal.add(tax).add(shippingAmount).subtract(discount);
        orders.setTotalAmount(total);
        ordersRepository.saveAndFlush(orders);

        //set order status
        OrderStatusHistory history=new OrderStatusHistory();
        history.setOrders(orders);
        history.setStatus("Order Created");
        history.setNote("Order created ");
        orderStatusHistoryRepository.save(history);

        return DataMapper.convertToOrderResponse(orders);
    }
    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }

    public Page<OrderResponseDto>getAllOrders(int page,int limit) {
        Pageable pageable= PageRequest.of(page,limit);
        return ordersRepository.findAll(pageable).map(DataMapper::convertToOrderResponse);
    }
    private Orders fetchOrder(long id){
        return ordersRepository.findById(id).orElseThrow(()->new ApiError("Order not found",HttpStatus.NOT_FOUND.value()));
    }

    public OrderResponseDto getOrder(long id) {
        Orders orders=fetchOrder(id);
        return DataMapper.convertToOrderResponse(orders);
    }
    private Products fetchProduct(long id){
        return productsRepository.findById(id).orElseThrow(()->new ApiError("no product found id:"+id,HttpStatus.NOT_FOUND.value()));
    }

    public void cancelOrder(@Valid CancelOrderDto request,long id) {
        Orders orders=fetchOrder(id);
        List<OrderItems>orderItems=orders.getOrderItems();
        for (OrderItems orderItem:orderItems){
            Products products=fetchProduct(orderItem.getProducts().getProductId());
            products.setStockQuantity(products.getStockQuantity()+orderItem.getQuantity());
            productsRepository.save(products);

            OrderStatusHistory history=orderStatusHistoryRepository.findByOrdersOrdersId(orders.getOrdersId()).orElseThrow(
                    ()->new ApiError("order staus history not found id:"+id,HttpStatus.NOT_FOUND.value())
            );
            history.setStatus("Order canceled");
            history.setNote(request.getReason());
            orderStatusHistoryRepository.save(history);

            InventoryTransaction transaction=new InventoryTransaction();
            transaction.setProducts(products);
            transaction.setTransactionType(TransactionType.IN);
            transaction.setQuantityChange(orderItem.getQuantity());
            transaction.setQuantityBefore(products.getStockQuantity());
            transaction.setQuantityAfter(products.getStockQuantity()+orderItem.getQuantity());
            transaction.setReferenceType(ReferenceType.RETURN);
            transaction.setReferenceId(orders.getOrdersId());
            inventoryTransactionRepository.save(transaction);

        }

        orders.setStatus(StatusEnum.CANCELLED);
        ordersRepository.save(orders);
    }

    public void createReview(@Valid ReviewDto request, long id) {
        Orders orders=fetchOrder(id);
        Products products=fetchProduct(request.getProductsId());
        Users users=authUtils.getCurrentUser();

        Review review=new Review();
        review.setOrders(orders);
        review.setUsers(users);
        review.setProducts(products);
        review.setText(request.getComment());
        review.setRating(request.getRating());

        reviewRepository.save(review);
    }

    public void updateOrderStatus(@Valid UpdateStatusDto request, long id) {
        Orders orders=fetchOrder(id);
        OrderStatusHistory history=orderStatusHistoryRepository.findByOrdersOrdersId(orders.getOrdersId()).orElseThrow(
                ()->new ApiError("history not found id:"+orders.getOrdersId(),HttpStatus.NOT_FOUND.value())
        );

        String status= request.getStatus().trim().toUpperCase();
        if (status.equals(StatusEnum.CONFIRMED.toString())){
            orders.setStatus(StatusEnum.CONFIRMED);
            history.setStatus("order confirmed");
            history.setNote("order confirmed");
        }else if (status.equals(StatusEnum.CANCELLED.toString())){
            orders.setStatus(StatusEnum.CANCELLED);
            history.setStatus("order cancelled");
            history.setNote("order cancelled");
        } else if (status.equals(StatusEnum.SHIPPED.toString())) {
           orders.setStatus(StatusEnum.SHIPPED);
            history.setStatus("order shipped");
            history.setNote("order shipped");
        }else if (status.equals(StatusEnum.DELIVERED.toString())){
            orders.setStatus(StatusEnum.DELIVERED);
            history.setStatus("order delivered");
            history.setNote("order delivered");
        }else if (status.equals(StatusEnum.PENDING.toString())){
            orders.setStatus(StatusEnum.PENDING);
            history.setStatus("order pending");
            history.setNote("order pending");
        }else {
            throw new ApiError("Invalid status ",HttpStatus.FORBIDDEN.value());
        }
        ordersRepository.save(orders);
        orderStatusHistoryRepository.save(history);
    }

    public List<OrderTimeLineDto> getTimelineHistory(long id) {
        List<OrderStatusHistory> history=orderStatusHistoryRepository.findAllByOrdersOrdersId(id);
        return  history.stream().map(DataMapper::convertToOrderTimeline).toList();
    }
}
