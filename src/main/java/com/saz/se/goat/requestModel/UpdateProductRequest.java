package com.saz.se.goat.requestModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest
{

    private long price;
    private long discountPercentage;
    private long rating;
    private int stock;
    private String thumbnail;
    private boolean bestSeller;
}
