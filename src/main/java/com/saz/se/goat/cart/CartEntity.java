package com.saz.se.goat.cart;

import com.saz.se.goat.article.ArticleEntity;
import com.saz.se.goat.order.OrderEntity;
import com.saz.se.goat.user.UserEntity;
import jakarta.persistence.*;
import java.util.List;



@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(generator = "cart_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "cart_gen", sequenceName = "cart_seq", initialValue = 547390, allocationSize = 1)
    private long id;
    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ArticleEntity> articles;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @OneToOne(fetch = FetchType.EAGER,mappedBy = "cartEntity", cascade = CascadeType.ALL)
    private OrderEntity orderEntity;
    private boolean active;

    public CartEntity() {}

    public CartEntity(List<ArticleEntity> articles, UserEntity userEntity)
    {
        this.articles = articles;
        this.userEntity = userEntity;
        this.active = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ArticleEntity> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleEntity> articles) {
        this.articles = articles;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addArticleToCart(ArticleEntity articleEntity)
    {
        articles.add(articleEntity);
    }
    public void addArticle(ArticleEntity article) {
        articles.add(article);
        article.setCartEntity(this);
    }

    @Override
    public String toString() {
        return "CartEntity{" +
                "id='" + id + '\'' +
                ", articles=" + articles +
                ", userEntity=" + userEntity +
                ", orderEntity=" + orderEntity +
                ", active=" + active +
                '}';
    }
}
