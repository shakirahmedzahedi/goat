package com.saz.se.goat.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


public class APIError
{
    private long timestamp;
    private int status;
    private ErrorModel error;
    private String path;
    private Object properties;
    private String action;

    public APIError(APIException exception, HttpStatus httpStatus, WebRequest request)
    {
        this.timestamp = System.currentTimeMillis();
        this.status = httpStatus.value();
        this.error = new ErrorModel(httpStatus.getReasonPhrase() + ": " + exception.getMessage(), exception.getCode());
        this.action = exception.getAction();

        if (request instanceof ServletWebRequest)
        {
            this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
        }
    }

    public APIError(APIException exception, HttpStatus httpStatus, Object properties, WebRequest request)
    {
        this(exception, httpStatus, request);

        this.properties = properties;
    }

    public APIError(APIException exception, HttpStatus httpStatus, String message, WebRequest request)
    {
        this(exception, httpStatus, request);
    }

    public APIError(String message, HttpStatus httpStatus, Object properties, WebRequest request)
    {
        this.timestamp = System.currentTimeMillis();
        this.status = httpStatus.value();
        this.error = new ErrorModel(message, "-1");

        this.properties = properties;

        if (request instanceof ServletWebRequest)
        {
            this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
        }
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public ErrorModel getError()
    {
        return error;
    }

    public void setError(ErrorModel error)
    {
        this.error = error;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public Object getProperties()
    {
        return properties;
    }

    public void setProperties(Object properties)
    {
        this.properties = properties;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }
}
