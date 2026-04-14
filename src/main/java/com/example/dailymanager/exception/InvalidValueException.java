package com.example.dailymanager.exception;

/**
 * 유효하지 않은 값이 제공된 경우 발생하는 예외 클래스. 요청 정보에 유효하지 않은 값이 포함되어 있을 때 이 예외가 발생한다.
 * Transactional을 사용하는 관계로 반드시 RuntimeException을 상속받아 예외 발생시 롤백이 되도록 하였다.
 */
public class InvalidValueException extends RuntimeException {
    public InvalidValueException() {
        super("Invalid value provided");
    }
}
