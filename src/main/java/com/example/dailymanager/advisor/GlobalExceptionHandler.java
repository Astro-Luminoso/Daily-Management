package com.example.dailymanager.advisor;

import com.example.dailymanager.exception.CommentExceedException;
import com.example.dailymanager.exception.EventNotFoundException;
import com.example.dailymanager.exception.InvalidValueException;
import com.example.dailymanager.exception.PasswordNotMatchException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger;

    public GlobalExceptionHandler() {
        logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Void> handleEventNotFoundException(
            EventNotFoundException e,
            HttpServletRequest req
    ) {
        logger.warn("{}: {} - Event Not Found", req.getMethod(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<Void> handleInvalidValueException(
            InvalidValueException e,
            HttpServletRequest req
    ) {
        logger.warn("{}: {} - Invalid Value Detected", req.getMethod(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Void> handlePasswordNotMatchException(
            PasswordNotMatchException e,
            HttpServletRequest req
    ) {
        logger.warn("{}: {} - Password is not match", req.getMethod(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(CommentExceedException.class)
    public ResponseEntity<Void> handleCommentExceedException(
            CommentExceedException e,
            HttpServletRequest req
    ) {
        logger.warn("{}: {} - Comment Exceed", req.getMethod(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
