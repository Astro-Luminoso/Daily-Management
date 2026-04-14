package com.example.dailymanager.exception;

/**
 * 존재하지 않는 일정에 대한 예외 클래스. 요청한 일정이 데이터베이스에 존재하지 않을 때 이 예외가 발생한다.
 * Transactional을 사용하는 관계로 반드시 RuntimeException을 상속받아 예외 발생시 롤백이 되도록 하였다.
 */
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException() {
        super("Event not found");
    }
}
