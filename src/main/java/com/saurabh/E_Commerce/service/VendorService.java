package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.AuthDtos.UserDto;
import com.saurabh.E_Commerce.dto.OrdersDto.OrderResponseDto;
import com.saurabh.E_Commerce.dto.ProductDtos.ProductDto;
import com.saurabh.E_Commerce.dto.ProductDtos.ProductRequestDto;
import com.saurabh.E_Commerce.dto.Vendors.UpdateVendorDto;
import com.saurabh.E_Commerce.dto.Vendors.VendorsDto;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Orders;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.models.Vendors;
import com.saurabh.E_Commerce.repository.OrdersRepository;
import com.saurabh.E_Commerce.repository.ProductsRepository;
import com.saurabh.E_Commerce.repository.VendorsRepository;
import com.saurabh.E_Commerce.security.AuthUtils;
import com.saurabh.E_Commerce.utils.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpClient;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VendorService {
    private final AuthUtils authUtils;
    private final VendorsRepository vendorsRepository;
    private final ProductsRepository productsRepository;
    private final ProductService productService;
    private final OrdersRepository ordersRepository;
    private final OrderService orderService;

    private Vendors fetchVendors(){
        Users users=authUtils.getCurrentUser();
        return vendorsRepository.findByUsersUserId(users.getUserId()).orElseThrow(
                ()->new ApiError("No such vendors id", HttpStatus.NOT_FOUND.value())
        );
    }
    public VendorsDto getProfile() {
        Vendors vendors=fetchVendors();
        return DataMapper.convertToUserDto(vendors);
    }

    public void updateVendor(UpdateVendorDto request) {
        Vendors vendors=fetchVendors();
        vendors.setBusinessName(request.getBusinessName());
        vendors.setGstNumber(request.getGstNumber());
        vendorsRepository.save(vendors);
    }

    public Page<ProductDto> getAllProducts(int page, int limit) {
        Vendors vendors=fetchVendors();
        Pageable pageable= PageRequest.of(page,limit);
        return productsRepository.findByVendorsUsers(vendors.getUsers(),pageable).map(DataMapper::convertToProductDto);
    }

    private ProductDto fetchProduct(long id){
        checkProductVendor(id);
       return productService.getProductById(id);
    }
    private void checkProductVendor(long id){
        ProductDto dto=fetchProduct(id);
        Vendors vendors=fetchVendors();
        if (dto.getVendorId()!= vendors.getId()){
            throw new ApiError("No product found id:"+vendors.getId()+" vendor id:"+vendors.getId(),HttpStatus.NOT_FOUND.value());
        }
    }

    public ProductDto getProducts(long id) {
        checkProductVendor(id);
        return fetchProduct(id);
    }

    public void updateProduct(long id, ProductRequestDto request) {
        checkProductVendor(id);
        productService.updateProduct(id,request);
    }

    public void createProduct(ProductRequestDto request) {
        productService.createProduct(request);
    }

    public void deleteProduct(long id) {
        checkProductVendor(id);
        productService.deleteProduct(id);
    }

    public List<OrderResponseDto> viewAllOrders() {
        Vendors vendors=fetchVendors();
        List<Orders>orders=ordersRepository.findAllByVendors(vendors.getId());
        return orders.stream().map(DataMapper::convertToOrderResponse).toList();
    }

    public OrderResponseDto viewOrders(long id) {
        Vendors vendors=fetchVendors();
        Orders orders=ordersRepository.findByVendors(vendors.getId(),id).orElseThrow(
                ()->new ApiError("No such orders",HttpStatus.NOT_FOUND.value())
        );
        return DataMapper.convertToOrderResponse(orders);
    }
}
