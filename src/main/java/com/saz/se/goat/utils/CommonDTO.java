package com.saz.se.goat.utils;

import com.saz.se.goat.article.ArticleDTO;
import com.saz.se.goat.article.ArticleEntity;
import com.saz.se.goat.cart.CartDTO;
import com.saz.se.goat.cart.CartEntity;
import com.saz.se.goat.discountCoupon.DiscountCouponDTO;
import com.saz.se.goat.discountCoupon.DiscountCouponEntity;
import com.saz.se.goat.model.Address;
import com.saz.se.goat.model.AddressDTO;
import com.saz.se.goat.order.OrderDTO;
import com.saz.se.goat.order.OrderEntity;
import com.saz.se.goat.product.ProductDTO;
import com.saz.se.goat.product.ProductEntity;
import com.saz.se.goat.user.UserDTO;
import com.saz.se.goat.user.UserEntity;

import java.util.stream.Collectors;

public class CommonDTO {
    public UserDTO toUserDTO(UserEntity userEntity) {
        AddressDTO addressDTO = null;
        if (userEntity.getAddress() != null) {
            addressDTO = new AddressDTO(
                    userEntity.getAddress().getApartmentNo(),
                    userEntity.getAddress().getHouseNo(),
                    userEntity.getAddress().getPostCode(),
                    userEntity.getAddress().getPostOffice(),
                    userEntity.getAddress().getCity());
        }



        return new UserDTO(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getPhoneNo(),
                userEntity.getRoles(),
                userEntity.isActive(),
                userEntity.isInitialDiscount(),
                addressDTO,
                userEntity.getOrderEntityList().stream()
                        .map(this::toOrderDTO)
                        .collect(Collectors.toList()),
                userEntity.getCartEntityList().stream()
                        .map(this::toCartDTO)
                        .collect(Collectors.toList()));

    }

    public ProductDTO toProductDTO(ProductEntity productEntity)
    {
        return new ProductDTO(
                productEntity.getId(),
                productEntity.getTitle(),
                productEntity.getDescription(),
                productEntity.getAdditionalInfo(),
                productEntity.getExtraInfo(),
                productEntity.getCategory(),
                productEntity.getPrice(),
                productEntity.getDiscountPercentage(),
                productEntity.getRating(),
                productEntity.getStock(),
                productEntity.getTags(),
                productEntity.getBrand(),
                productEntity.getSize(),
                productEntity.getWeight(),
                productEntity.getThumbnail());

    }

    public ArticleDTO toArticleDTO(ArticleEntity articleEntity)
    {
        ProductDTO productDTO = new ProductDTO(
                articleEntity.getProduct().getId(),
                articleEntity.getProduct().getTitle(),
                articleEntity.getProduct().getDescription(),
                articleEntity.getProduct().getAdditionalInfo(),
                articleEntity.getProduct().getExtraInfo(),
                articleEntity.getProduct().getCategory(),
                articleEntity.getProduct().getPrice(),
                articleEntity.getProduct().getDiscountPercentage(),
                articleEntity.getProduct().getRating(),
                articleEntity.getProduct().getStock(),
                articleEntity.getProduct().getTags(),
                articleEntity.getProduct().getBrand(),
                articleEntity.getProduct().getSize(),
                articleEntity.getProduct().getWeight(),
                articleEntity.getProduct().getThumbnail());



        return new ArticleDTO(
                articleEntity.getId(),
                productDTO,
                articleEntity.getUnit());
    }


    public CartDTO toCartDTO(CartEntity cartEntity)
    {
        if (cartEntity != null) {
            AddressDTO addressDTO = null;

            if (cartEntity.getUserEntity().getAddress() != null){
                addressDTO = new AddressDTO(
                        cartEntity.getUserEntity().getAddress().getApartmentNo(),
                        cartEntity.getUserEntity().getAddress().getHouseNo(),
                        cartEntity.getUserEntity().getAddress().getPostCode(),
                        cartEntity.getUserEntity().getAddress().getPostOffice(),
                        cartEntity.getUserEntity().getAddress().getCity());
        }

            UserDTO userDTO = new UserDTO (
                    cartEntity.getUserEntity().getId(),
                    cartEntity.getUserEntity().getFirstName(),
                    cartEntity.getUserEntity().getLastName(),
                    cartEntity.getUserEntity().getEmail(),
                    cartEntity.getUserEntity().getPhoneNo(),
                    cartEntity.getUserEntity().getRoles(),
                    cartEntity.getUserEntity().isActive(),
                    cartEntity.getUserEntity().isInitialDiscount(),
                    addressDTO);

            return new CartDTO(
                    cartEntity.getId(),
                    cartEntity.getArticles().stream()
                            .map(this::toArticleDTO)
                            .collect(Collectors.toList()),
                    userDTO,
                    cartEntity.isActive());
        }
        else
        {
            return null;
        }
    }

    public OrderDTO toOrderDTO(OrderEntity orderEntity)
    {
        UserDTO userDTO = new UserDTO(
                orderEntity.getUserEntity().getId(),
                orderEntity.getUserEntity().getFirstName(),
                orderEntity.getUserEntity().getLastName(),
                orderEntity.getUserEntity().getEmail(),
                orderEntity.getUserEntity().getPhoneNo(),
                orderEntity.getUserEntity().getRoles(),
                orderEntity.getUserEntity().isActive(),
                orderEntity.getUserEntity().isInitialDiscount());

        DiscountCouponDTO discountCouponDTO = null;
        if (orderEntity.getDiscountCoupon() != null) {
            discountCouponDTO = new DiscountCouponDTO(
                    orderEntity.getDiscountCoupon().getId(),
                    orderEntity.getDiscountCoupon().getNumber(),
                    orderEntity.getDiscountCoupon().getDiscountAmmount(),
                    orderEntity.getDiscountCoupon().isAlreadyUsed(),
                    orderEntity.getDiscountCoupon().getCreatedAt(),
                    orderEntity.getDiscountCoupon().getUpdatedAt());
        }

        CartDTO cartDTO = new CartDTO (
                orderEntity.getCartEntity().getId(),
                orderEntity.getCartEntity().getArticles().stream()
                        .map(this::toArticleDTO)
                        .collect(Collectors.toList()),
                userDTO,
                orderEntity.getCartEntity().isActive());

        return new OrderDTO(
                orderEntity.getId(),
                cartDTO,
                userDTO,
                orderEntity.getAddress(),
                orderEntity.getPaymentMethod().name(),
                orderEntity.getPaymentStatus().name(),
                orderEntity.getOrderStatus().name(),
                discountCouponDTO,
                orderEntity.getTotalAmount(),
                orderEntity.getVatOrTex(),
                orderEntity.getCreatedAt(),
                orderEntity.getUpdatedAt());

    }

    public DiscountCouponDTO toDiscountCouponDTO(DiscountCouponEntity discountCouponEntity)
    {
        UserDTO userDTO = new UserDTO (
                discountCouponEntity.getCouponCreator().getId(),
                discountCouponEntity.getCouponCreator().getFirstName(),
                discountCouponEntity.getCouponCreator().getLastName(),
                discountCouponEntity.getCouponCreator().getEmail(),
                discountCouponEntity.getCouponCreator().getPhoneNo(),
                discountCouponEntity.getCouponCreator().getRoles(),
                discountCouponEntity.getCouponCreator().isActive(),
                discountCouponEntity.getCouponCreator().isInitialDiscount());

        return new DiscountCouponDTO(
                discountCouponEntity.getId(),
                discountCouponEntity.getNumber(),
                discountCouponEntity.getDiscountAmmount(),
                discountCouponEntity.isAlreadyUsed(),
                discountCouponEntity.getCreatedAt(),
                discountCouponEntity.getUpdatedAt(),
                userDTO);
    }

    public AddressDTO addressDTO(Address address)
    {
        return new AddressDTO(
                address.getApartmentNo(),
                address.getHouseNo(),
                address.getPostCode(),
                address.getPostOffice(),
                address.getCity());
    }
}
