package com.saz.se.goat.order;

import com.saz.se.goat.cart.CartEntity;
import com.saz.se.goat.cart.CartRepository;
import com.saz.se.goat.discountCoupon.DiscountCouponEntity;
import com.saz.se.goat.discountCoupon.DiscountCouponRepository;
import com.saz.se.goat.model.*;
import com.saz.se.goat.product.ProductRepository;
import com.saz.se.goat.requestModel.OrderRequest;
import com.saz.se.goat.user.UserEntity;
import com.saz.se.goat.user.UserRepository;
import com.saz.se.goat.utils.CommonDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderService
{

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DiscountCouponRepository discountCouponRepository;
    @Autowired
    CartRepository cartRepository;
    CommonDTO commonDTO = new CommonDTO();;

    @Transactional
    public OrderDTO createOrder(OrderRequest orderRequest)
    {

        UserEntity user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        CartEntity cart = cartRepository.findById(orderRequest.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        Address address = (orderRequest.getAddress() != null) ? orderRequest.getAddress() : user.getAddress();


        DiscountCouponEntity discountCoupon = (orderRequest.getDiscountCouponNumber() != null)
                ? discountCouponRepository.findByNumber(orderRequest.getDiscountCouponNumber()).orElse(null):null;


        PaymentMethod paymentMethod = PaymentMethod.valueOf(orderRequest.getPaymentMethod().toUpperCase());
        PaymentStatus paymentStatus = PaymentStatus.valueOf(orderRequest.getPaymentStatus().toUpperCase());
        OrderStatus orderStatus = OrderStatus.valueOf(orderRequest.getOrderStatus().toUpperCase());

        OrderEntity order = new OrderEntity(cart, user, address, paymentMethod, paymentStatus, orderStatus, discountCoupon);

        orderRepository.save(order);
        user.getOrderEntityList().add(order);
        cart.setActive(false);
        cartRepository.save(cart);
        userRepository.save(user);

        return commonDTO.toOrderDTO(order);

    }

    public OrderDTO getOrderById(long id)
    {
        OrderEntity orderEntity = orderRepository.getReferenceById(id);

        return commonDTO.toOrderDTO(orderEntity);
    }

}
