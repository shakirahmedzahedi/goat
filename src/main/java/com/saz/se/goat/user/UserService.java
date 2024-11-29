package com.saz.se.goat.user;

import com.saz.se.goat.article.ArticleEntity;
import com.saz.se.goat.article.ArticleRepository;
import com.saz.se.goat.article.ArticleRequest;
import com.saz.se.goat.cart.CartDTO;
import com.saz.se.goat.cart.CartEntity;
import com.saz.se.goat.cart.CartRepository;
import com.saz.se.goat.model.*;
import com.saz.se.goat.product.ProductEntity;
import com.saz.se.goat.product.ProductRepository;
import com.saz.se.goat.requestModel.FavoriteRequest;
import com.saz.se.goat.requestModel.UpdateAddressRequest;
import com.saz.se.goat.response.UserResponse;
import com.saz.se.goat.utils.CommonDTO;
import com.saz.se.goat.utils.JWTService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;



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
    public Optional<CartDTO> addToCart(ArticleRequest request)
    {
        UserEntity user = repo.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));


        CartEntity cart = cartRepository.findActiveCartByUserId(user.getId()).orElseGet(() ->
        {
            CartEntity newCart = new CartEntity(new ArrayList<>(),user);
            user.addCartToUser(newCart);
            user.getCartEntityList().add(newCart);
            cartRepository.save(newCart);
            repo.save(user);
            return newCart;
        });


        Optional<ArticleEntity> existingArticleOpt = cart.getArticles().stream()
                .filter(article -> article.getProduct().getId() == product.getId())
                .findFirst();

        if (existingArticleOpt.isPresent())
        {
            ArticleEntity existingArticle = existingArticleOpt.get();
            existingArticle.setUnit(existingArticle.getUnit() + request.getUnit());
            articleRepository.save(existingArticle);

        }
        else
        {
            ArticleEntity newArticle = new ArticleEntity(product, request.getUnit());
            cart.addArticle(newArticle);
            articleRepository.save(newArticle);
            cartRepository.save(cart);
        }

        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        return Optional.of(commonDTO.toCartDTO(cart));
    }

    @Transactional
    public Optional<CartDTO> removeFromCart(ArticleRequest request)
    {

        UserEntity user = repo.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));


        CartEntity cart = cartRepository.findActiveCartByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));


        Optional<ArticleEntity> existingArticleOpt = cart.getArticles().stream()
                .filter(article -> article.getProduct().getId() == product.getId())
                .findFirst();

        if (existingArticleOpt.isPresent())
        {
            ArticleEntity existingArticle = existingArticleOpt.get();

            if (existingArticle.getUnit() > 1)
            {
                existingArticle.setUnit(existingArticle.getUnit() - 1);
                articleRepository.save(existingArticle);
            }
            else
            {
                cart.getArticles().remove(existingArticle);
                articleRepository.delete(existingArticle);
                cartRepository.save(cart);
            }

            product.setStock(product.getStock() + 1);
            productRepository.save(product);

        }
        else
        {
            throw new EntityNotFoundException("Product not found in the cart");
        }

        // Return the updated user DTO
        return Optional.of(commonDTO.toCartDTO(cart));
    }

    public Optional<CartDTO> deleteArticle(ArticleRequest request) {
        UserEntity user = repo.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        CartEntity cart = cartRepository.findActiveCartByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        Optional<ArticleEntity> existingArticleOpt = cart.getArticles().stream()
                .filter(article -> article.getProduct().getId() == product.getId())
                .findFirst();

        if (existingArticleOpt.isPresent())
        {
            ArticleEntity existingArticle = existingArticleOpt.get();
            cart.getArticles().remove(existingArticle);
            articleRepository.delete(existingArticle);
            cartRepository.save(cart);
            product.setStock(product.getStock() + request.getUnit());
            productRepository.save(product);
        }
        else
        {
            throw new EntityNotFoundException("Product not found in the cart");
        }

        if (cart.getArticles().size() < 1)
        {
            user.getCartEntityList().remove(cart);
            cartRepository.delete(cart);
            repo.save(user);
            cart = cartRepository.findActiveCartByUserId(request.getUserId()).orElse(null);
        }

        return Optional.ofNullable(commonDTO.toCartDTO(cart));
    }



    public Optional<CartDTO> feachActiveCartsByUser(long userId)
    {
        UserEntity user = repo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

       /* CartEntity cart = cartRepository.findActiveCartByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));*/
        CartEntity cart = cartRepository.findActiveCartByUserId(user.getId())
                .orElse(null);

        return Optional.ofNullable(commonDTO.toCartDTO(cart));

    }

    public UserDTO updateAddress(UpdateAddressRequest request)
    {
        UserEntity user = repo.findById(request.getUserId())
                .orElse(null);
        Address address = new Address(request.getApartmentNo(), request.getHouseNo(), request.getPostCode(), request.getPostOffice(), request.getCity());

        user.setAddress(address);
        repo.save(user);

        return commonDTO.toUserDTO(user);

    }

    public Optional<UserDTO> addToFavorite(FavoriteRequest request)
    {
        Optional<UserEntity> optionalUser = repo.findById(request.getUserId());
        Optional<ProductEntity> optionalProduct = productRepository.findById(request.getProductId());

        if (optionalUser.isPresent() && optionalProduct.isPresent())
        {
            UserEntity user = optionalUser.get();
            ProductEntity product = optionalProduct.get();

            user.addToFavorite(product);
            repo.save(user);
            return Optional.ofNullable(commonDTO.toUserDTO(user));
        }
        return null;
    }

    public Optional<UserDTO> removeFromFavorite(FavoriteRequest request)
    {
        Optional<UserEntity> optionalUser = repo.findById(request.getUserId());
        Optional<ProductEntity> optionalProduct = productRepository.findById(request.getProductId());

        if (optionalUser.isPresent() && optionalProduct.isPresent())
        {
            UserEntity user = optionalUser.get();
            ProductEntity product = optionalProduct.get();

            user.removeFromFavorite(product);
            repo.save(user);
            return Optional.ofNullable(commonDTO.toUserDTO(user));
        }
        return null;
    }

    public Optional<UserDTO> fetchUser(long userId)
    {
        Optional<UserEntity> optionalUser = repo.findById(userId);
        if (optionalUser.isPresent() )
        {
            UserEntity user = optionalUser.get();
            return Optional.ofNullable(commonDTO.toUserDTO(user));
        }
        return null;
    }
}