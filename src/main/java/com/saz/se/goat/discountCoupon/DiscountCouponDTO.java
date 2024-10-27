package com.saz.se.goat.discountCoupon;

import com.saz.se.goat.user.UserDTO;
import com.saz.se.goat.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCouponDTO
{
    private long id;
    private String number;
    private long discountAmount;
    private boolean alreadyUsed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO userDTO;

    public DiscountCouponDTO(long id, String number, long discountAmount, boolean alreadyUsed, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.number = number;
        this.discountAmount = discountAmount;
        this.alreadyUsed = alreadyUsed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
