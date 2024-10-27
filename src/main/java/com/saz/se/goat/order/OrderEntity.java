package com.saz.se.goat.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saz.se.goat.article.ArticleEntity;
import com.saz.se.goat.cart.CartEntity;
import com.saz.se.goat.discountCoupon.DiscountCouponEntity;
import com.saz.se.goat.model.Address;
import com.saz.se.goat.model.OrderStatus;
import com.saz.se.goat.model.PaymentMethod;
import com.saz.se.goat.model.PaymentStatus;
import com.saz.se.goat.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "goat")
public class OrderEntity {
    @Id
    @GeneratedValue(generator = "order_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "order_gen", sequenceName = "order_seq", initialValue = 545390, allocationSize = 1)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @Embedded
    private Address address;
    @NotNull(message = "PaymentMethod is required")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull(message = "PaymentStatus is required")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @NotNull(message = "OrderStatus is required")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_coupon_id")
    private DiscountCouponEntity discountCoupon;
    @Column(nullable = false)
    @Min(value = 0, message = "TotalAmount must be greater than or equal to 0")
    @NotNull(message = "TotalAmount is required")
    private long totalAmount;
    @Column(nullable = false)
    @Min(value = 0, message = "vatOrTex must be greater than or equal to 0")
    @NotNull(message = "vatOrTex is required")
    private long vatOrTex;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    public OrderEntity(CartEntity cartEntity, UserEntity userEntity, Address address, PaymentMethod paymentMethod,
                       PaymentStatus paymentStatus, OrderStatus orderStatus, DiscountCouponEntity discountCoupon)
    {
        this.cartEntity = cartEntity;
        this.userEntity = userEntity;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.discountCoupon = discountCoupon;
        this.totalAmount = calculateTotalAmount();
        this.vatOrTex = calculateVatOrTex(5);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public DiscountCouponEntity getDiscountCoupon() {
        return discountCoupon;
    }

    public void setDiscountCoupon(DiscountCouponEntity discountCoupon) {
        this.discountCoupon = discountCoupon;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getVatOrTex() {
        return vatOrTex;
    }

    public void setVatOrTex(long vatOrTex) {
        this.vatOrTex = vatOrTex;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public long calculateTotalAmount()
    {
        long sum = cartEntity.getArticles().stream()
                .mapToLong(article -> article.getProduct().getPrice() * article.getUnit())
                .sum();

        if(discountCoupon != null)
        {
            sum = sum - applyDiscount(discountCoupon);
        }

        if (userEntity.isInitialDiscount())
        {
            sum = sum - 200;
        }
        return  sum;
    }

    private long applyDiscount(DiscountCouponEntity discountCoupon)
    {
        return discountCoupon.getDiscountAmmount();
    }

    // Method to calculate the VAT or Tax based on products
    public long calculateVatOrTex(long vatPercentage) {
        return calculateTotalAmount() * (vatPercentage / 100);
    }
}
