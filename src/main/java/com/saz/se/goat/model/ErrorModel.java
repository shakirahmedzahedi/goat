package com.saz.se.goat.model;

public class ErrorModel {
    private String code;
    private String message;

    public ErrorModel() {}

    public ErrorModel(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorModel{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

