package com.saz.se.goat.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO
{
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

}
