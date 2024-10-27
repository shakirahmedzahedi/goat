package com.saz.se.goat.order;

import com.saz.se.goat.article.ArticleDTO;
import com.saz.se.goat.cart.CartDTO;
import com.saz.se.goat.discountCoupon.DiscountCouponDTO;
import com.saz.se.goat.discountCoupon.DiscountCouponEntity;
import com.saz.se.goat.model.Address;
import com.saz.se.goat.model.OrderStatus;
import com.saz.se.goat.model.PaymentMethod;
import com.saz.se.goat.model.PaymentStatus;
import com.saz.se.goat.user.UserDTO;
import com.saz.se.goat.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO
{
    private long id;
    private CartDTO cart;
    private UserDTO user;
    private Address address;
    private String paymentMethod;
    private String paymentStatus;
    private String orderStatus;
    private DiscountCouponDTO discountCoupon;
    private long totalAmount;
    private long vatOrTex;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
