package com.example.dailymanager.exception;

/**
 * 유효하지 않은 비밀번호가 제공된 경우 발생하는 예외 클래스. 로그인 시 입력한 비밀번호가 실제 비밀번호와 일치하지 않을 때 이 예외가 발생한다.
 * Transactional을 사용하는 관계로 반드시 RuntimeException을 상속받아 예외 발생시 롤백이 되도록 하였다.
 */
public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException() {
        super("Password does not match");
    }
}
