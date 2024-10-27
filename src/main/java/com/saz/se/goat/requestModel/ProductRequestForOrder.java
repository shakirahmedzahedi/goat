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
public class ProductRequestForOrder extends ProductEntity {
    private long id;
    private String title;
    private String description;
    private String additionalInfo;
    private String extraInfo;
    private String category;
    private long price;
    private long discountPercentage;
    private long rating;
    private int stock;
    private String tags;
    private String brand;
    private String size;
    private long weight;
    private String thumbnail;
    private int quantity;
}
