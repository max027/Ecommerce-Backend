package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.*;
import com.saurabh.E_Commerce.dto.ProductDtos.ImageDto;
import com.saurabh.E_Commerce.dto.ProductDtos.ProductDto;
import com.saurabh.E_Commerce.dto.ProductDtos.ProductRequestDto;
import com.saurabh.E_Commerce.dto.ReviewDto.ReviewsDto;
import com.saurabh.E_Commerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
    public ResponseEntity<Page<ProductDto>>getAllProducts(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam int minPrice,
            @RequestParam int maxPrice
    ){
        return ResponseEntity.ok(productService.getAllProduct(page,limit,minPrice,maxPrice));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
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
    @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
    public ResponseEntity<ProductDto>getBySlug(@PathVariable String slug){
        return ResponseEntity.ok(productService.getBySlug(slug));
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR') or hasAuthority('CREATE_PRODUCT')")
    public ResponseEntity<ProductDto>createProduct(@Valid @RequestBody ProductRequestDto request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR') or hasAuthority('UPDATE_PRODUCT')")
    public ResponseEntity<ProductDto>updateProduct(@PathVariable long id,@Valid @RequestBody ProductRequestDto request){
        return ResponseEntity.ok(productService.updateProduct(id,request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR') or hasAuthority('DELETE_PRODUCT')")
    public ResponseEntity<?>updateProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR') or hasAuthority('UPDATE_PRODUCT')")
    public ResponseEntity<?>addImage(@PathVariable long id,@Valid @RequestBody ImageDto image){
        productService.addImage(id,image);
        return ResponseEntity.ok("image added");
    }

    @DeleteMapping("/{id}/images/{imageId}")
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR') or hasAuthority('UPDATE_PRODUCT')")
    public ResponseEntity<?>deleteImage(@PathVariable long id,@PathVariable long imageId){
        productService.removeImage(id,imageId);
        return ResponseEntity.ok("image removed");
    }

    @PostMapping("/{id}/stock/")
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR') or hasAuthority('UPDATE_PRODUCT')")
    public ResponseEntity<?>updateStock(@PathVariable long id, @Valid  @RequestBody StockDto stockDto){
        productService.updateStock(id,stockDto);
        return ResponseEntity.ok("image removed");
    }
}
