package com.saz.se.goat.model;

public class APIException extends RuntimeException
{
    private String code;
    private String action;

    @Override
    public String toString()
    {
        return "message=" + getMessage() + ", code=" + getCode() + ", action=" + getAction();
    }

    public APIException(String message)
    {
        super(message);
    }

    public APIException(String message, String code, String action)
    {
        super(message);
        this.code = code;
        this.action = action;
    }

    public APIException(String message, String code)
    {
        super(message);
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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
