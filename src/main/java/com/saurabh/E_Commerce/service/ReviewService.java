package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.ReviewDto.ReviewRequestDto;
import com.saurabh.E_Commerce.dto.ReviewDto.ReviewsDto;
import com.saurabh.E_Commerce.dto.ReviewDto.UpdateReviewDto;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Orders;
import com.saurabh.E_Commerce.models.Products;
import com.saurabh.E_Commerce.models.Review;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.OrdersRepository;
import com.saurabh.E_Commerce.repository.ProductsRepository;
import com.saurabh.E_Commerce.repository.ReviewRepository;
import com.saurabh.E_Commerce.repository.UserRepository;
import com.saurabh.E_Commerce.security.AuthUtils;
import com.saurabh.E_Commerce.utils.DataMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductsRepository productsRepository;
    private final OrdersRepository ordersRepository;
    private final AuthUtils authUtils;

    private Products fetchProduct(long id){
        return productsRepository.findById(id).orElseThrow(()->new ApiError("no product find id:"+id, HttpStatus.NOT_FOUND.value()));
    }

    public List<ReviewsDto> getProductReview(long id) {
        Products products=fetchProduct(id);
        List<Review>reviews=products.getReviews();
        return reviews.stream().map(DataMapper::converToReviewsDto).toList();
    }

    public void createReview(@Valid ReviewRequestDto request) {
        Users users=authUtils.getCurrentUser();
        Products products=fetchProduct(request.getProductId());
        Orders orders=ordersRepository.findById(request.getOrderId()).orElseThrow();
        Review review=reviewRepository.findByUsersAndProducts(users,products).orElse(null);
        if (review!=null){
            throw new ApiError("User already reviewed",HttpStatus.CONFLICT.value());
        }
        review=new Review();
        review.setProducts(products);
        review.setOrders(orders);
        review.setUsers(users);
        review.setText(request.getText());
        review.setRating(request.getRating());

        reviewRepository.save(review);
    }

    public void reviewUpdate(@Valid UpdateReviewDto request,long id) {
        Review review=reviewRepository.findById(id).orElseThrow(()->new ApiError("review does not exist",HttpStatus.NOT_FOUND.value()));
        review.setReviewId(id);

        review.setRating(request.getRating());
        review.setText(request.getText());

        reviewRepository.save(review);
    }

    public void deleteReview(long id) {
        Review review=reviewRepository.findById(id).orElseThrow(()->new ApiError("review does not exist",HttpStatus.NOT_FOUND.value()));
        reviewRepository.delete(review);
    }
}
