package com.saurabh.E_Commerce.controller;

import com.saurabh.E_Commerce.dto.ReviewDto.ReviewRequestDto;
import com.saurabh.E_Commerce.dto.ReviewDto.ReviewsDto;
import com.saurabh.E_Commerce.dto.ReviewDto.UpdateReviewDto;
import com.saurabh.E_Commerce.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviwes")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ReviewsDto>>getProductReview(@PathVariable long id){
       return ResponseEntity.ok(reviewService.getProductReview(id));
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<?>createReview(@Valid  @RequestBody ReviewRequestDto request){
        reviewService.createReview(request);
        return ResponseEntity.ok("review added");
    }

    @PutMapping("/reviews/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<?>updateReview(@Valid  @RequestBody UpdateReviewDto request, @PathVariable long id){
        reviewService.reviewUpdate(request,id);
        return ResponseEntity.ok("review updated");
    }

    @DeleteMapping("/reviews/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<?>deleteReview( @PathVariable long id){
        reviewService.deleteReview(id);
        return ResponseEntity.ok("review removed");
    }
}
