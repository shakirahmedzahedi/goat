package com.saz.se.goat.user;

import com.saz.se.goat.article.ArticleEntity;
import com.saz.se.goat.article.ArticleRepository;
import com.saz.se.goat.article.ArticleRequest;
import com.saz.se.goat.cart.CartEntity;
import com.saz.se.goat.cart.CartRepository;
import com.saz.se.goat.model.*;
import com.saz.se.goat.product.ProductEntity;
import com.saz.se.goat.product.ProductRepository;
import com.saz.se.goat.response.UserResponse;
import com.saz.se.goat.utils.CommonDTO;
import com.saz.se.goat.utils.JWTService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.saz.se.goat.auth.AuthenticationService.parseUser;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepository repo;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProductRepository productRepository;
    CommonDTO commonDTO = new CommonDTO();


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserEntity register(UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

    public String verify(UserEntity user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        } else {
            return "fail";
        }
    }

    /*public Optional<UserDTO> addRoleByUserId(long id, String role)
    {
        UserEntity user = repo.getReferenceById(id);
        if (!user.getRoles().contains(Role.valueOf(role)))
        {
            user.getRoles().add(Role.valueOf(role));
        }
        else
        {
           // response.addError( new ErrorModel("14470","Role is already Assign"));
            return Optional.empty();
        }
        repo.save(user);

        return Optional.ofNullable(commonDTO.toUserDTO(user));
    }*/

    /*public List<UserDTO> allusers()
    {
       List<UserEntity> users = repo.findAll();
       return users.stream()
               .map(commonDTO :: toUserDTO)
               .collect(Collectors.toList());
    }*/

    @Transactional
    public Optional<UserDTO> addToCart( ArticleRequest request)
    {
        UserEntity user = repo.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Check if the user has an active cart
        CartEntity cart = cartRepository.findActiveCartByUserId(user.getId()).orElseGet(() -> {
            // Create a new cart if no active cart exists
            CartEntity newCart = new CartEntity(new ArrayList<>(),user);
            user.addCartToUser(newCart);
            user.getCartEntityList().add(newCart);
            cartRepository.save(newCart); // Save the new cart
            repo.save(user); // Save the user to persist the new cart association
            return newCart;
        });

        // Check if the cart already contains an article for the product
        Optional<ArticleEntity> existingArticleOpt = cart.getArticles().stream()
                .filter(article -> article.getProduct().getId() == product.getId())
                .findFirst();

        if (existingArticleOpt.isPresent())
        {
            // If article exists, update the unit count
            ArticleEntity existingArticle = existingArticleOpt.get();
            existingArticle.setUnit(existingArticle.getUnit() + request.getUnit());
            articleRepository.save(existingArticle); // Save the updated article
        }
        else
        {
            // If article does not exist, create a new one and add it to the cart
            ArticleEntity newArticle = new ArticleEntity(product, request.getUnit());
            cart.addArticle(newArticle);
            articleRepository.save(newArticle); // Save the new article
            cartRepository.save(cart); // Save the cart to persist the new article association
        }


        return Optional.ofNullable(commonDTO.toUserDTO(user));
    }
}