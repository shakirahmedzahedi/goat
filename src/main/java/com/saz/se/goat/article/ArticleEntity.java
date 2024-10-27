package com.saz.se.goat.article;

import com.saz.se.goat.cart.CartEntity;
import com.saz.se.goat.order.OrderEntity;
import com.saz.se.goat.product.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;


@Entity
@AllArgsConstructor
@Table(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue(generator = "article_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "article_gen", sequenceName = "article_seq", initialValue = 548390, allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    private int unit;

    public ArticleEntity() {}

    public ArticleEntity (ProductEntity product, int unit)
    {
        this.product = product;
        this.unit = unit;
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

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "id=" + id +
                ", cartEntity=" + cartEntity +
                ", product=" + product +
                ", unit=" + unit +
                '}';
    }
}