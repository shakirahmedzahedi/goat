package com.saz.se.goat.requestModel;


import com.saz.se.goat.model.Category;
import com.saz.se.goat.model.Tags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String title;
    private String description;
    private String additionalInfo;
    private String extraInfo;
    private Category category;
    private long price;//this is price
    private long discountPercentage;
    private long rating;
    private int stock;
    private Tags tags;
    private String brand;
    private String size;
    private long weight;
    private String thumbnail;
}
