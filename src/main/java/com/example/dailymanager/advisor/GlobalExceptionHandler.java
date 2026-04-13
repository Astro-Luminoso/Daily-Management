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

    private ResponseEntity<Void> buildResponse(
            HttpServletRequest req,
            Exception e,
            HttpStatus status
    ) {
        logger.warn("{} {} : Client Error {} - {}",
                req.getMethod(), req.getRequestURI(), status.value(), e.getMessage());
        return ResponseEntity.status(status).build();
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Void> handleEventNotFoundException(
            EventNotFoundException e,
            HttpServletRequest req
    ) {
        return buildResponse(req, e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<Void> handleInvalidValueException(
            InvalidValueException e,
            HttpServletRequest req
    ) {
        return buildResponse(req, e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Void> handlePasswordNotMatchException(
            PasswordNotMatchException e,
            HttpServletRequest req
    ) {
        return buildResponse(req, e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CommentExceedException.class)
    public ResponseEntity<Void> handleCommentExceedException(
            CommentExceedException e,
            HttpServletRequest req
    ) {
        return buildResponse(req, e, HttpStatus.BAD_REQUEST);
    }
}
