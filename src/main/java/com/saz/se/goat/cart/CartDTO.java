package com.saz.se.goat.cart;

import com.saz.se.goat.article.ArticleDTO;
import com.saz.se.goat.article.ArticleEntity;
import com.saz.se.goat.order.OrderDTO;
import com.saz.se.goat.user.UserDTO;
import com.saz.se.goat.user.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO
{
    private long id;
    List<ArticleDTO> articles;
    UserDTO user;
    boolean active;
}
