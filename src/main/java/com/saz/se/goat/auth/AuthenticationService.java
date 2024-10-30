package com.saz.se.goat.auth;

import com.saz.se.goat.model.ErrorModel;
import com.saz.se.goat.model.ResponseWrapper;
import com.saz.se.goat.requestModel.SignInRequest;
import com.saz.se.goat.requestModel.SignUpRequest;
import com.saz.se.goat.response.UserResponse;
import com.saz.se.goat.user.UserDTO;
import com.saz.se.goat.user.UserEntity;
import com.saz.se.goat.user.UserRepository;
import com.saz.se.goat.utils.CommonDTO;
import com.saz.se.goat.utils.EmailService;
import com.saz.se.goat.utils.JWTService;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    CommonDTO commonDTO = new CommonDTO();

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseWrapper<String> signup(SignUpRequest request) throws MessagingException {

        ResponseWrapper<String> response = new ResponseWrapper<>();

        boolean isExit = userRepository.findByEmail(request.getEmail()).isPresent();

        if (isExit) {
            response.addError(new ErrorModel("14461", "E-mail Already is use! "));
            return response;
        }

        UserEntity user = new UserEntity (
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                encoder.encode(request.getPassword()),
                request.getPhoneNo(),
                request.getAddress());

        userRepository.save(user);
        try
        {
            emailService.sendConfirmationEmail(user);
            response.setData("User registered. Please check your email for verification.");
        }
        catch (MessagingException ex)
        {
            response.addError(new ErrorModel("14462", "Could not send Email"));

        }
        emailService.sendConfirmationEmail(user);

        return response;

    }


    public String verify(UserEntity user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        } else {
            return "fail";
        }
    }

    public ResponseWrapper<String> activeAccount(String token) {
        ResponseWrapper<String> response = new ResponseWrapper<>();
        String email;
        try
        {
            email = jwtService.extractUserName(token);
        }
        catch (JwtException ex)
        {
            response.addError(new ErrorModel("14463", ex.getMessage()));
            return response;
        }
        UserEntity user  = userRepository.findByEmail(email).orElse(null);

        if (user == null)
        {
            response.addError(new ErrorModel("14462", "User not valid"));
            return response;
        }
        else
        {
           user.setActive(true);
           userRepository.save(user);
        }
        response.setData("User Active. Please Log in");
        return  response;
    }

    public UserDTO signIn(SignInRequest request)
    {
        try
        {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserEntity user = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (authentication.isAuthenticated() && user.isActive())
            {
                return commonDTO.toUserDTO(user);

            }

        } catch (BadCredentialsException e) {
            // Handle invalid credentials
             ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

        }


        return  null;
    }

    public static UserResponse parseUser (UserEntity req)
    {
        return new UserResponse(req.getId(),req.getFirstName(), req.getLastName(), req.getEmail(), req.getPhoneNo(),
                req.getRoles(), req.isActive(), req.isInitialDiscount(), req.getAddress());
    }
}
