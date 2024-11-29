package com.saz.se.goat.user;

import com.saz.se.goat.article.ArticleEntity;
import com.saz.se.goat.article.ArticleRequest;
import com.saz.se.goat.cart.CartDTO;
import com.saz.se.goat.model.ErrorModel;
import com.saz.se.goat.model.ResponseWrapper;
import com.saz.se.goat.requestModel.FavoriteRequest;
import com.saz.se.goat.requestModel.UpdateAddressRequest;
import com.saz.se.goat.utils.HeaderProperties;
import com.saz.se.goat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController
{
    @Autowired
    UserService userService;
    @Autowired
    JsonUtils jsonUtils;


    @CrossOrigin
    @GetMapping("/feachActiveCartsByUser")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> feachActiveCartsByUser (@RequestParam long userId, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper <Optional<CartDTO>> response = new ResponseWrapper<>();
        Optional<CartDTO> cartDTO = userService.feachActiveCartsByUser(userId);

        if (cartDTO.isPresent())
        {
            response.setData(cartDTO);

        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @GetMapping("/fetchUser")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> fetchUser (@RequestParam long userId, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper <Optional<UserDTO>> response = new ResponseWrapper<>();
        Optional<UserDTO> userDTO = userService.fetchUser(userId);

        if (userDTO.isPresent())
        {
            response.setData(userDTO);

        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @PostMapping("/addToCart")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> addToCart (@RequestBody ArticleRequest article, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper <Optional<CartDTO>> response = new ResponseWrapper<>();
        Optional<CartDTO> cartDTO = userService.addToCart(article);

        if (cartDTO.isPresent())
        {
            response.setData(cartDTO);

        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @PostMapping("/addToFavorite")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> addToFavorite (@RequestBody FavoriteRequest request, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper <Optional<UserDTO>> response = new ResponseWrapper<>();
        Optional<UserDTO> userDTO = userService.addToFavorite(request);

        if (userDTO.isPresent())
        {
            response.setData(userDTO);

        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @PostMapping("/removeFromFavorite")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> removeFromFavorite (@RequestBody FavoriteRequest request, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper <Optional<UserDTO>> response = new ResponseWrapper<>();
        Optional<UserDTO> userDTO = userService.removeFromFavorite(request);

        if (userDTO.isPresent())
        {
            response.setData(userDTO);

        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @PostMapping("/removeFromCart")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> removeFromCart (@RequestBody ArticleRequest article, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper <Optional<CartDTO>> response = new ResponseWrapper<>();
        Optional<CartDTO> cartDTO = userService.removeFromCart(article);

        if (cartDTO.isPresent())
        {
            response.setData(cartDTO);

        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @PostMapping("/deleteArticle")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> deleteArticle (@RequestBody ArticleRequest request , @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper <Optional<CartDTO>> response = new ResponseWrapper<>();
        Optional<CartDTO> cartDTO = userService.deleteArticle(request);

        if (cartDTO.isPresent())
        {
            response.setData(cartDTO);

        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @PatchMapping("/updateAddress")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> updateAddress (@RequestBody UpdateAddressRequest updateAddressRequest, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper <UserDTO> response = new ResponseWrapper<>();
        UserDTO userDTO = userService.updateAddress(updateAddressRequest);

        response.setData(userDTO);


        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }
}
