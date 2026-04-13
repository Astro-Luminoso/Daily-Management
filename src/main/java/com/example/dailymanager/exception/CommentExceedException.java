package com.example.dailymanager.exception;

public class CommentExceedException extends RuntimeException {
    public CommentExceedException() {
        super("Comments exceeded for this event");
    }
}
