package com.saz.se.goat.discountCoupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCouponRepository extends JpaRepository<DiscountCouponEntity, String>
{
    Optional<DiscountCouponEntity> findByNumber(String discountCouponNumber);
}
