package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.CategoryDto;
import com.saurabh.E_Commerce.dto.CategoryRequestDto;
import com.saurabh.E_Commerce.dto.ProductDto;
import com.saurabh.E_Commerce.models.Categories;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.service.CategoryService;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>>getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto>getAllCategory(@PathVariable  long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoryDto>getBySlug(@PathVariable  String slug){
        return ResponseEntity.ok(categoryService.getBySlug(slug));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>>getProductOfCategory(@PathVariable long id){
        return ResponseEntity.ok(categoryService.getProductOfCategory(id));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>createCategory(@Valid @RequestBody CategoryRequestDto request){
        categoryService.createCategory(request);
        return ResponseEntity.ok("Category created");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>updateCategory(@Valid @RequestBody CategoryRequestDto request,@PathVariable long id){
        categoryService.updateCategory(request,id);
        return ResponseEntity.ok("Category updated");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

