package com.saz.se.goat.admin;

import com.saz.se.goat.discountCoupon.DiscountCouponDTO;
import com.saz.se.goat.discountCoupon.DiscountCouponEntity;
import com.saz.se.goat.discountCoupon.DiscountCouponRepository;
import com.saz.se.goat.model.*;
import com.saz.se.goat.order.OrderDTO;
import com.saz.se.goat.order.OrderEntity;
import com.saz.se.goat.order.OrderRepository;
import com.saz.se.goat.product.ProductEntity;
import com.saz.se.goat.product.ProductRepository;
import com.saz.se.goat.requestModel.DiscountCouponRequest;
import com.saz.se.goat.requestModel.OrderUpdateRequest;
import com.saz.se.goat.requestModel.ProductRequest;
import com.saz.se.goat.user.UserDTO;
import com.saz.se.goat.user.UserEntity;
import com.saz.se.goat.user.UserRepository;
import com.saz.se.goat.utils.CommonDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService
{
    @Autowired
    UserRepository repo;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DiscountCouponRepository discountCouponRepository;
    CommonDTO commonDTO = new CommonDTO();

    public Optional<ProductEntity> addProduct(ProductRequest request)
    {
        ProductEntity product = new ProductEntity(request.getTitle(), request.getDescription(), request.getAdditionalInfo(),
                request.getExtraInfo(), request.getCategory().name(), request.getPrice(), request.getDiscountPercentage(),
                request.getRating(), request.getStock(), request.getTags().name(),request.getBrand(), request.getSize(),
                request.getWeight(), request.getThumbnail());

        productRepository.save(product);

        return Optional.of(product);
    }

    public Optional<ProductEntity> updateProductById(long id, ProductRequest request)
    {
        ProductEntity product = productRepository.getReferenceById(id);
        product.setTitle(request.getTitle());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setAdditionalInfo(product.getAdditionalInfo());
        product.setDiscountPercentage(request.getDiscountPercentage());
        product.setCategory(request.getCategory().name());
        product.setStock(request.getStock());
        product.setSize(request.getSize());
        product.setExtraInfo(request.getExtraInfo());
        product.setTags(request.getTags().name());
        product.setPrice(request.getPrice());
        product.setThumbnail(request.getThumbnail());
        product.setWeight(request.getWeight());
        product.setRating(request.getRating());

        productRepository.save(product);

        return Optional.of(product);
    }

    public Optional<ProductEntity> deleteProductById(long id) {
        productRepository.deleteById(id);

        return Optional.empty();
    }

    public Optional<UserDTO> addRoleByUserId(long id, String role)
    {
        UserEntity userEntity = repo.getReferenceById(id);
        if (!userEntity.getRoles().contains(Role.valueOf(role)))
        {
            userEntity.getRoles().add(Role.valueOf(role));
        }
        else
        {
            // response.addError( new ErrorModel("14470","Role is already Assign"));
            return Optional.empty();
        }
        repo.save(userEntity);

        return Optional.ofNullable(commonDTO.toUserDTO(userEntity));
    }

    public List<UserDTO> allusers()
    {
        List<UserEntity> userEntityList = repo.findAll();
        return userEntityList.stream()
                .map(commonDTO :: toUserDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO updateOrder(long id, OrderUpdateRequest request)
    {
        OrderEntity orderEntity = orderRepository.getReferenceById(id);
        orderEntity.setOrderStatus(OrderStatus.valueOf(request.getOrderStatus()));
        orderEntity.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        orderEntity.setPaymentStatus(PaymentStatus.valueOf(request.getPaymentStatus()));

        orderRepository.save(orderEntity);

        return commonDTO.toOrderDTO(orderEntity);
    }

    public OrderDTO deleteOrderById(long id)
    {

        OrderEntity orderEntity = orderRepository.getReferenceById(id);
        orderRepository.delete(orderEntity);

        return commonDTO.toOrderDTO(orderEntity);
    }

    public List<OrderDTO> getAllOrder()
    {
        List<OrderEntity> orderEntityList = orderRepository.findAll();
        return orderEntityList.stream()
                .map(commonDTO::toOrderDTO)
                .collect(Collectors.toList());

    }

    public DiscountCouponDTO addNewCoupon(DiscountCouponRequest request)
    {
        UserEntity user = repo.getReferenceById(request.getUserId());
        DiscountCouponEntity discountCouponEntity = new DiscountCouponEntity(request.getDiscountAmount(), user);
        discountCouponRepository.save(discountCouponEntity);

        return commonDTO.toDiscountCouponDTO(discountCouponEntity);
    }
}
