package com.saz.se.goat.requestModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest
{
    private String paymentMethod;
    private String paymentStatus;
    private String orderStatus;
}
