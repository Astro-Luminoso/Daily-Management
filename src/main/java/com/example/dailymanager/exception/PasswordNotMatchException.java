package com.example.dailymanager.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException() {
        super("Password does not match");
    }
}
