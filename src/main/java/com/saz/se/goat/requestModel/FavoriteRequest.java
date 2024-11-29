package com.saz.se.goat.requestModel;

import com.saz.se.goat.product.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequest
{
    private long userId;
    private long productId;
}
