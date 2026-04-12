package com.example.dailymanager.exception;

public class CommentExceedException extends RuntimeException {
    public CommentExceedException() {
        super("Comment limit exceeded for this event");
    }
}
