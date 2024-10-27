package com.saz.se.goat.utils;

import org.springframework.http.HttpHeaders;

import java.util.Properties;

public class HeaderProperties extends Properties {
    private String email;
    public HeaderProperties()
    {

    }
    public HeaderProperties(HttpHeaders header)
    {
        init(header);
    }

    public void init(HttpHeaders headers)
    {
        this.putAll(headers);
        //this.email = getHeaders(headers, "email", "");
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
