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

/**
 * API 작동에 필요한 글로벌 REST 예외 처리 handler.
 * 앱 Exceptions가 HTTP status 코드로 연결될 수 있도록 매핑함과 동시에 클라이언트 에러 관련 로그를 기록한다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger;

    public GlobalExceptionHandler() {
        logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    }

    /**
     * 실질적인 응답 빌드 메서드. 애러 관련 로그를 기록함과 동시에 응답을 빌드한다.
     *
     * @param req API에게 들어온 HTTP 요청 정보
     * @param e 발생한 예외 객체
     * @param status 응답에 필요한 HTTP status 코드
     * @return 클라이언트에게 반환할 HTTP 응답 객체
     */
    private ResponseEntity<Void> buildResponse(
            HttpServletRequest req,
            Exception e,
            HttpStatus status
    ) {
        logger.warn("{} {} : Client Error {} - {}",
                req.getMethod(), req.getRequestURI(), status.value(), e.getMessage());
        return ResponseEntity.status(status).build();
    }

    /**
     * 존재하지 않는 일정에 대한 예외 처리 핸들러. 404 Not Found 응답을 반환한다.
     *
     * @param e 예외 객체, EventNotFoundException
     * @param req API에게 들어온 HTTP 요청 정보
     * @return 클라이언트에게 반환할 HTTP 응답 객체
     */
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Void> handleEventNotFoundException(
            EventNotFoundException e,
            HttpServletRequest req
    ) {
        return buildResponse(req, e, HttpStatus.NOT_FOUND);
    }

    /**
     * HTTP 요청의 바디에 있는 입력값이 유효하지 아니할 때 발생하는 예외 처리 헨들러.
     * 400 Bad Request 응답을 반환한다.
     *
     * @param e 예외 객체, InvalidValueException
     * @param req API에게 들어온 HTTP 요청 정보
     * @return 클라이언트에게 반환할 HTTP 응답 객체
     */
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<Void> handleInvalidValueException(
            InvalidValueException e,
            HttpServletRequest req
    ) {
        return buildResponse(req, e, HttpStatus.BAD_REQUEST);
    }

    /**
     * 댓글 수정/삭제 시 입력된 패스워드가 일치하지 않을 때 발생하는 예외 처리 핸들러.
     * 401 Unauthorized 응답을 반환한다.
     *
     * @param e 예외 객체, PasswordNotMatchException
     * @param req API에게 들어온 HTTP 요청 정보
     * @return 클라이언트에게 반환할 HTTP 응답 객체
     */
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Void> handlePasswordNotMatchException(
            PasswordNotMatchException e,
            HttpServletRequest req
    ) {
        return buildResponse(req, e, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 댓글이 일정당 최대 개수(10개)를 초과하여 작성될 때 발생하는 예외 처리 핸들러.
     * 403 Forbidden 응답을 반환한다.
     *
     * @param e 예외 객체, CommentExceedException
     * @param req API에게 들어온 HTTP 요청 정보
     * @return 클라이언트에게 반환할 HTTP 응답 객체
     */
    @ExceptionHandler(CommentExceedException.class)
    public ResponseEntity<Void> handleCommentExceedException(
            CommentExceedException e,
            HttpServletRequest req
    ) {
        return buildResponse(req, e, HttpStatus.FORBIDDEN);
    }
}
