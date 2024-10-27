package com.saz.se.goat.product;

import com.saz.se.goat.model.ResponseWrapper;
import com.saz.se.goat.requestModel.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService
{
    @Autowired
    ProductRepository productRepository;

    public  Optional<ProductEntity> getProductById(long id)
    {
        ProductEntity product = productRepository.getReferenceById(id);

        return Optional.of(product);
    }

    public List<ProductEntity> getAllProducts()
    {
        List<ProductEntity> productList = productRepository.findAll();

        return productList;
    }

}
