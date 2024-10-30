package com.saz.se.goat.discountCoupon;

import com.saz.se.goat.model.ResponseWrapper;
import com.saz.se.goat.requestModel.DiscountCouponRequest;
import com.saz.se.goat.user.UserEntity;
import com.saz.se.goat.user.UserRepository;
import com.saz.se.goat.utils.CommonDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountCouponService
{

    @Autowired
    DiscountCouponRepository discountCouponRepository;
    CommonDTO commonDTO = new CommonDTO();


    public DiscountCouponDTO getCoupon(String couponNumber)
    {
        DiscountCouponEntity discountCouponEntity = discountCouponRepository.findByNumber(couponNumber)
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found"));
        return commonDTO.toDiscountCouponDTO(discountCouponEntity);
    }
}
