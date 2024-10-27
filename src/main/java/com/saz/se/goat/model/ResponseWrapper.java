package com.saz.se.goat.model;

import java.util.ArrayList;
import java.util.List;

public class ResponseWrapper<T> {
    private List<ErrorModel> errors = new ArrayList<>();
    private List<ErrorModel> warnings = new ArrayList<>();
    private String token;
    private T data;

    public ResponseWrapper() {}

    // Getters and Setters
    public List<ErrorModel> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorModel> errors) {
        this.errors = errors;
    }

    public List<ErrorModel> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<ErrorModel> warnings) {
        this.warnings = warnings;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Utility methods to add errors/warnings
    public void addError(ErrorModel error) {
        this.errors.add(error);
    }

    public void addWarning(ErrorModel warning) {
        this.warnings.add(warning);
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "errors=" + errors +
                ", warnings=" + warnings +
                ", token='" + token + '\'' +
                ", data=" + data +
                '}';
    }
}
