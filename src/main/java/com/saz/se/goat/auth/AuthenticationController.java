package com.saz.se.goat.auth;

import com.saz.se.goat.model.ResponseWrapper;
import com.saz.se.goat.requestModel.SignInRequest;
import com.saz.se.goat.requestModel.SignUpRequest;
import com.saz.se.goat.utils.JsonUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    JsonUtils jsonUtils;

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request, @RequestHeader HttpHeaders header) throws MessagingException {
        ResponseWrapper response = authenticationService.signup(request);
        return jsonUtils.responseAsJson(response);
    }

    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest request, @RequestHeader HttpHeaders header) {

        ResponseWrapper response = authenticationService.signIn(request);
        return jsonUtils.responseAsJsonWithToken(response,request.getEmail());
    }

    @CrossOrigin
    @GetMapping("/confirmEmail")
    public ResponseEntity<?> activeAccount(@RequestParam String token, @RequestHeader HttpHeaders header) {
        ResponseWrapper response = authenticationService.activeAccount(token);
        return jsonUtils.responseAsJson(response);
    }
}