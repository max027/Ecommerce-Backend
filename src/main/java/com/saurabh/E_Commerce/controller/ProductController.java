package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.*;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<Page<ProductDto>>getAllProducts(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam int minPrice,
            @RequestParam int maxPrice
    ){
        return ResponseEntity.ok(productService.getAllProduct(page,limit,minPrice,maxPrice));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<Page<ReviewsDto>>getAllReviews(
            @RequestParam int page,
            @RequestParam int limit
    ){
        return ResponseEntity.ok(productService.getAllReviews(page,limit));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductDto>getAllReviews(@PathVariable String slug){
        return ResponseEntity.ok(productService.getBySlug(slug));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<ProductDto>createProduct(@Valid @RequestBody ProductRequestDto request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto>updateProduct(@PathVariable long id,@Valid @RequestBody ProductRequestDto request){
        return ResponseEntity.ok(productService.updateProduct(id,request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?>updateProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/images")
    public ResponseEntity<?>addImage(@PathVariable long id,@Valid @RequestBody ImageDto image){
        productService.addImage(id,image);
        return ResponseEntity.ok("image added");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/images/{imageId}")
    public ResponseEntity<?>addImage(@PathVariable long id,@PathVariable long imageId){
        productService.removeImage(id,imageId);
        return ResponseEntity.ok("image removed");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/stock/")
    public ResponseEntity<?>updateStock(@PathVariable long id, @Valid  @RequestBody StockDto stockDto){
        productService.updateStock(id,stockDto);
        return ResponseEntity.ok("image removed");
    }
}
