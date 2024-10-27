package com.saz.se.goat.user;

import com.saz.se.goat.article.ArticleEntity;
import com.saz.se.goat.article.ArticleRequest;
import com.saz.se.goat.model.ErrorModel;
import com.saz.se.goat.model.ResponseWrapper;
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
    @PostMapping("/addToCart")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> addToCart (@RequestBody ArticleRequest article, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<Optional<UserDTO>> response = new ResponseWrapper<>();
        Optional<UserDTO> userDTO = userService.addToCart(article);

        if (userDTO.isPresent())
        {
            response.setData(userDTO);

        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }
}
