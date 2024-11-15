package com.saz.se.goat.utils;

import com.saz.se.goat.model.APIError;
import com.saz.se.goat.model.ErrorModel;
import com.saz.se.goat.model.ResponseWrapper;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


// Exception handler for all controllers
@ControllerAdvice
@Component
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle invalid JSON or unreadable request bodies
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        APIError apiError = new APIError("Malformed JSON request", HttpStatus.BAD_REQUEST, null, request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Handle validation errors for method arguments
  /*  @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ResponseWrapper response = new ResponseWrapper() {
            public Object getResponse() {
                return "";
            }
        };

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            response.getErrors().add(new ErrorModel(error.getField() + " " + error.getDefaultMessage(), "-1"));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            response.getErrors().add(new ErrorModel(error.getObjectName() + " " + error.getDefaultMessage(), "-1"));
        }

        logger.error(response);
        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }
*/
    // Handle constraint violations, typically for bean validation failures
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String[] errors = ex.getMessage().split(", ");
        for (int i = 0; i < errors.length; i++) {
            errors[i] = errors[i].replaceAll("^.+\\.", "");
        }
        APIError apiError = new APIError("Invalid request parameter(s)", HttpStatus.BAD_REQUEST, errors, request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Handle internal server exceptions
   /* @ExceptionHandler(InternalServerException.class)
    protected ResponseEntity<Object> handleInternalServerException(InternalServerException ex, WebRequest request) {
        APIError apiError = new APIError(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getType(), request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // Handle gateway errors
    @ExceptionHandler(GatewayException.class)
    protected ResponseEntity<Object> handleGatewayException(GatewayException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_GATEWAY;
        APIError apiError = new APIError(ex, status, ex.getProperties(), request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    // Handle unauthorized exceptions
    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        APIError apiError = new APIError("Unauthorized access", HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    // Handle bad request exceptions
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        APIError apiError = new APIError("Bad request", HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Handle unprocessable entity exceptions
    @ExceptionHandler(UnprocessableEntityException.class)
    protected ResponseEntity<Object> handleUnprocessableEntityException(UnprocessableEntityException ex, WebRequest request) {
        APIError apiError = new APIError("Unprocessable entity", HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }*/
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        APIError apiError = new APIError("Email not found", HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
        ResponseWrapper<?> responseWrapper = new ResponseWrapper<>();
        responseWrapper.addError(new ErrorModel("1111", ex.getMessage()));
        return handleExceptionInternal(ex, responseWrapper, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
    // Handle entity not found exceptions
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        APIError apiError = new APIError("Entity not found", HttpStatus.NOT_FOUND, ex.getMessage(), request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /*@ExceptionHandler(SignatureException.class)
    protected ResponseEntity<Object> handleSignatureException(SignatureException ex, WebRequest request) {
        APIError apiError = new APIError("Invalid or expired JWT token", HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }*/

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex) {
        // Log the exception for debugging
        System.out.println("Invalid JWT signature: " + ex.getMessage());

        // Return a custom error response to the client
        return new ResponseEntity<>("Invalid JWT signature", HttpStatus.UNAUTHORIZED);
    }
    // Handle JWT exceptions for invalid or expired tokens
    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<Object> handleJwtException(JwtException ex, WebRequest request) {
        APIError apiError = new APIError("Invalid or expired JWT token", HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}

