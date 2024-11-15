package com.saz.se.goat.discountCoupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saz.se.goat.order.OrderEntity;
import com.saz.se.goat.user.UserEntity;
import com.saz.se.goat.utils.GoatUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@NoArgsConstructor
@Table(name = "discount_coupon")
public class DiscountCouponEntity
{
    @Id
    @GeneratedValue(generator = "dc_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "dc_gen", sequenceName = "dc_seq", initialValue = 546390, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(nullable = false, unique = true)
    private String number;
    @Column(nullable = false)
    private long discountAmmount;
    @OneToOne(fetch = FetchType.EAGER,mappedBy = "discountCoupon", cascade = CascadeType.ALL)
    private OrderEntity orderEntity;
    private boolean alreadyUsed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity couponCreator;

    public DiscountCouponEntity(long discountAmmount, UserEntity couponCreator)
    {
        this.number = GoatUtils.generateRandomString();
        this.discountAmmount = discountAmmount;
        this.alreadyUsed = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.couponCreator = couponCreator;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public long getDiscountAmmount() {
        return discountAmmount;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDiscountAmmount(long discountAmmount) {
        this.discountAmmount = discountAmmount;
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public UserEntity getCouponCreator() {
        return couponCreator;
    }

    public void setCouponCreator(UserEntity couponCreator) {
        this.couponCreator = couponCreator;
    }

    @Override
    public String toString() {
        return "DiscountCouponEntity{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", discountAmmount=" + discountAmmount +
                ", alreadyUsed=" + alreadyUsed +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", couponCreator=" + couponCreator +
                '}';
    }
}
