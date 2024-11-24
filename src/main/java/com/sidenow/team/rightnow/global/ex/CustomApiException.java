package com.sidenow.team.rightnow.global.ex;

public class CustomApiException extends RuntimeException{
    public CustomApiException(String message) {
        super(message);
    }
}
