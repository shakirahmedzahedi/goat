package com.saz.se.goat.utils;

import com.saz.se.goat.model.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JsonUtils {
    @Autowired
    JWTService jwtService;

    public ResponseEntity<?> responseAsJson(Object object)
    {
        return ResponseEntity.ok(object);
    }

    public ResponseEntity<?> responseAsJsonWithToken(ResponseWrapper response, String subject)
    {
        String token = jwtService.generateToken(subject);
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

}
