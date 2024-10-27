package com.saz.se.goat.article;

import com.saz.se.goat.cart.CartDTO;
import com.saz.se.goat.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO
{
    private long id;
   // private CartDTO cart;
    private ProductDTO product;
    private int unit;
}
