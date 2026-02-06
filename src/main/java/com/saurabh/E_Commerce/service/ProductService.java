package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.*;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Categories;
import com.saurabh.E_Commerce.models.ProductImage;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.repository.CategoriesRepository;
import com.saurabh.E_Commerce.repository.ProductImageRepository;
import com.saurabh.E_Commerce.repository.ProductsRepository;
import com.saurabh.E_Commerce.utils.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductImageRepository productImageRepository;

    private Products fetchProduct(long id){
        return productsRepository.findById(id).orElseThrow(
                ()->new ApiError("No product found id:"+id, HttpStatus.NOT_FOUND.value())
        );
    }

    public Page<ProductDto> getAllProduct(int page,int limit,int minPrice,int maxPrice) {
        Pageable pageable= PageRequest.of(page,limit);
        return productsRepository.findAllRange(pageable,minPrice,maxPrice).map(DataMapper::convertToProductDto);
    }

    public ProductDto getProductById(long id) {
       Products products=fetchProduct(id);
       return DataMapper.convertToProductDto(products);
    }

    public Page<ReviewsDto> getAllReviews(int page, int limit) {
        Pageable pageable= PageRequest.of(page,limit);
        return productsRepository.findAllReviews(pageable).map(DataMapper::converToReviewsDto);
    }

    public ProductDto getBySlug(String slug) {
        Products products=productsRepository.findBySlug(slug).orElseThrow(
                ()->new ApiError("product not found "+slug,HttpStatus.NOT_FOUND.value())
        );
        return DataMapper.convertToProductDto(products);
     }

    public ProductDto createProduct(ProductRequestDto request) {
        Products products=new Products();
        return saveProduct(products,request);
    }
    private ProductDto saveProduct(Products products,ProductRequestDto request){
        products.setActive(true);
        products.setName(request.getName());

        Categories categories=categoriesRepository.findById(request.getCategoryId()).orElseThrow(
                ()->new ApiError("Category not found id:"+request.getCategoryId(),HttpStatus.NOT_FOUND.value())
        );
        products.setCategories(categories);
        products.setPrice(request.getPrice());
        products.setDiscountPrice(request.getDiscountPrice());
        products.setDescription(request.getDescription());
        products.setSlug(request.getSlug());
        products.setSku(request.getSku());
        List<ProductImage>productImages=new ArrayList<>();
        for (String img: request.getImageUrls()){
            ProductImage productImage=new ProductImage();
            productImage.setProducts(products);
            productImage.setImageUrl(img);
            productImages.add(productImage);
           productImageRepository.save(productImage);
        }
        products.setImages(productImages);

        productsRepository.save(products);
        return DataMapper.convertToProductDto(products);
    }

    public ProductDto updateProduct(long id, ProductRequestDto request) {
        Products products=fetchProduct(id);
        return saveProduct(products,request);
    }

    public void deleteProduct(long id) {
        Products products=fetchProduct(id);
        productsRepository.delete(products);
    }

    public void addImage(long id,ImageDto image) {
        Products products=fetchProduct(id);
        ProductImage productImage=new ProductImage();
        productImage.setProducts(products);
        productImage.setImageUrl(image.getUrl());
        productImageRepository.save(productImage);
    }

    public void removeImage(long id, long imageId) {
        Products products=fetchProduct(id);
        ProductImage productImage=productImageRepository.findByProductImageIdAndProducts(imageId,products).orElseThrow(
                ()->new ApiError("no image found id:"+imageId,HttpStatus.NOT_FOUND.value())
        );
        productImageRepository.delete(productImage);
    }

    public void updateStock(long id, StockDto stockDto) {
        Products products=fetchProduct(id);
        String action=stockDto.getAction();
        int quantity=stockDto.getQuantity();
        if (action.equals("set")){
            products.setStockQuantity(quantity);
        }else if (action.equals("add")){
            products.setStockQuantity(products.getStockQuantity()+quantity);
        }else{
            throw new ApiError("Invalid Action "+action,HttpStatus.FORBIDDEN.value());
        }
        productsRepository.save(products);
    }
}
