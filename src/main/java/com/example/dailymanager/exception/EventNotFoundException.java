package com.example.dailymanager.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException() {
        super("Event not found");
    }
}
