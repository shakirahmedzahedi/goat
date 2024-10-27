package com.saz.se.goat.requestModel;

import com.saz.se.goat.article.ArticleEntity;
import com.saz.se.goat.cart.CartEntity;
import com.saz.se.goat.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest
{
    private long cartId;
    private long userId;
    private Address address;
    private String paymentMethod;
    private String paymentStatus;
    private String orderStatus;
    private String discountCouponNumber;
}
