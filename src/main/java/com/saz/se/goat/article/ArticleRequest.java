package com.saz.se.goat.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest
{
    private long userId;
    private long productId;
    private int unit;
}
