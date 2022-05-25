package com.api.twstock.exception;

public class ApiException extends RuntimeException{

    public ApiException(String message){
        super(message);
    }
}
