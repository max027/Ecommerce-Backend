package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.CategoryDto;
import com.saurabh.E_Commerce.dto.CategoryRequestDto;
import com.saurabh.E_Commerce.dto.ProductDto;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Categories;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.repository.CategoriesRepository;
import com.saurabh.E_Commerce.utils.DataMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class CategoryService {
    private final CategoriesRepository categoriesRepository;


    public List<CategoryDto> getAllCategory() {
        List<Categories>categories=categoriesRepository.findAll();
        return categories.stream().map(DataMapper::convertToCategoryDto).toList();
    }

    public CategoryDto getById(@NotNull  long id) {
       return DataMapper.convertToCategoryDto(categoriesRepository.findById(id).orElseThrow(
               ()->new ApiError("Category does not exist:"+id, HttpStatus.NOT_FOUND.value())
       ));
    }

    public CategoryDto getBySlug(@NotNull String slug) {
        return DataMapper.convertToCategoryDto(categoriesRepository.findBySlug(slug).orElseThrow(
                ()->new ApiError("Category does not exist:"+slug, HttpStatus.NOT_FOUND.value())
        ));
    }
    private Categories fetchCategories(@NotNull long id){
        return categoriesRepository.findById(id).orElseThrow(
                ()->new ApiError("Category does not exist:"+id, HttpStatus.NOT_FOUND.value())
        );
    }

    public List<ProductDto> getProductOfCategory(@NotNull long id) {
       Categories categories=fetchCategories(id);

       List<Products>products=categories.getProducts();

       return products.stream().map(DataMapper::convertToProductDto).toList();
    }

    public void createCategory(@Valid CategoryRequestDto request) {
        Categories categories=categoriesRepository.findBySlug(request.getSlug()).orElse(null);
        if (categories!=null){
            throw new ApiError("category "+request.getName()+" already exists",HttpStatus.CONFLICT.value());
        }
        categories=new Categories();
        categories.setName(request.getName());
        categories.setSlug(request.getSlug());
        categories.setActive(request.isActive());
        categories.setDescription(request.getDescription());

        Categories parent=categoriesRepository.findById(request.getParentId()).orElse(null);
        categories.setParentId(parent);

        categoriesRepository.save(categories);

    }

    public void updateCategory(@Valid CategoryRequestDto request, @NotNull long id) {
        Categories categories=fetchCategories(id);
        categories.setCategoryId(id);

        categories.setName(request.getName());
        categories.setSlug(request.getSlug());
        categories.setActive(request.isActive());
        categories.setDescription(request.getDescription());

        Categories parent=categoriesRepository.findById(request.getParentId()).orElse(null);
        categories.setParentId(parent);

        categoriesRepository.save(categories);
    }

    public void deleteCategory(@NotNull long id) {
        Categories categories=fetchCategories(id);
        categoriesRepository.delete(categories);
    }
}
