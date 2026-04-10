package com.example.dailymanager.exception;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException() {
        super("Invalid value provided");
    }
}
