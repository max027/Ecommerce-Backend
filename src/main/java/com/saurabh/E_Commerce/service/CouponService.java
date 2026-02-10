package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.Coupon.CouponRequestDto;
import com.saurabh.E_Commerce.dto.Coupon.CouponsResponseDto;
import com.saurabh.E_Commerce.dto.Coupon.ValidateCouponDto;
import com.saurabh.E_Commerce.dto.Coupon.ValidateCouponResponse;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Coupons;
import com.saurabh.E_Commerce.repository.CouponsRepository;
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
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class CouponService {
    private final CouponsRepository couponsRepository;
    public ValidateCouponResponse validate(@Valid ValidateCouponDto request) {

        ValidateCouponResponse response=new ValidateCouponResponse();
        Coupons coupons=couponsRepository.findByCodeAndIsActiveTrue(request.getCode()).orElse(null);
        if (coupons==null){
            response.setValid(false);
            response.setMessage("Invalid coupon code");
            return  response;
        }
        if (coupons.getValidUntil().isBefore(Instant.now())){
            response.setValid(false);
            response.setMessage("coupon code expired");
            return  response;
        }
        if (request.getCartTotal().compareTo(BigDecimal.valueOf(coupons.getMinOrderValue()))<0){
            response.setValid(false);
            response.setMessage("minimum order value is "+coupons.getMinOrderValue());
            return  response;
        }
        if (coupons.getUsageCoupon()>coupons.getUsageLimit()){
            response.setValid(false);
            response.setMessage("cannot apply coupon usage limit exceeded");
            return  response;
        }

        BigDecimal discount;
        if (coupons.getDiscountType().equals("percentage")){
            discount=request.getCartTotal().multiply(BigDecimal.valueOf(coupons.getDiscountValue())).divide(BigDecimal.valueOf(100));
        }else {
            discount=BigDecimal.valueOf(coupons.getDiscountValue());
        }
        response.setValid(true);
        response.setMessage("Coupon applied sucessfully");
        response.setDiscountAmount(discount);
        response.setFinalAmount(request.getCartTotal().subtract(discount));

        return response;
    }

    public Page<CouponsResponseDto> getAllCoupons(int page, int limit) {
        Pageable pageable= PageRequest.of(page,limit);
        return couponsRepository.findAll(pageable).map(DataMapper::convertToCoupon);
    }
    private Coupons fetchCoupon(long id){
        return couponsRepository.findById(id).orElseThrow(
                ()->new ApiError("coupon does not exist id:"+id, HttpStatus.NOT_FOUND.value())
        );
    }
    public CouponsResponseDto getById(long id){
        Coupons coupons=fetchCoupon(id);
        return DataMapper.convertToCoupon(coupons);
    }

    public void createCoupon(CouponRequestDto request) {
        Coupons coupons=couponsRepository.findByCode(request.getCode()).orElse(null);
        if (coupons!=null){
            throw new ApiError("coupon already exists" ,HttpStatus.CONFLICT.value());
        }
        coupons=new Coupons();
        saveCoupon(request,coupons);
    }
    private void saveCoupon(CouponRequestDto request,Coupons coupons){
        coupons.setCode(request.getCode());
        coupons.setDescription(request.getDescription());
        coupons.setUsageCoupon(request.getUsageCoupon());
        coupons.setIsActive(request.isActive());
        coupons.setUsageLimit(request.getUsageLimit());
        coupons.setDiscountType(request.getDiscountType());
        coupons.setDiscountValue(request.getDiscountValue());
        coupons.setMaxDiscountValue(request.getMaxDiscountValue());
        coupons.setValidFrom(request.getValidFrom());
        coupons.setValidUntil(request.getValidUntil());
        couponsRepository.save(coupons);
    }

    public void updateCoupon(CouponRequestDto request,long id) {
       Coupons coupons=fetchCoupon(id);
       coupons.setCouponsId(id);
       saveCoupon(request,coupons);
    }

    public void deleteCoupon(long id) {
        Coupons coupons=fetchCoupon(id);
        if (coupons.isActive()){
            throw new ApiError("Coupon is active, first set coupon to inactive",HttpStatus.FORBIDDEN.value());
        }
        couponsRepository.delete(coupons);
    }

    public void changeStatus(String toggle,long id) {
        Coupons coupons=fetchCoupon(id);
        if (toggle.equals("true")){
            if (coupons.isActive()){
                throw new ApiError("coupon is already active",HttpStatus.CONFLICT.value());
            }
            coupons.setIsActive(true);
            couponsRepository.save(coupons);
        }else if (toggle.equals("false")){
            if (!coupons.isActive()){
                throw new ApiError("coupon is already de active",HttpStatus.CONFLICT.value());
            }
            coupons.setIsActive(false);
            couponsRepository.save(coupons);
        }else {
            throw new ApiError("invalid action",HttpStatus.FORBIDDEN.value());
        }
    }
}
