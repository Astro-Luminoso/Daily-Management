package com.example.dailymanager.dto;

/**
 * 요청정보의 유효성을 검증하기 위한 인터페이스.
 * 이 인터페이스를 구현하는 DTO 클래스는 isInvalid() 메서드를 통해 자신의 유효성을 검증할 수 있다.
 * isInvalid() 메서드는 요청 정보가 유효하지 않은 경우 true를 반환하고, 유효한 경우 false를 반환해야 한다.
 */
public interface Validatable {

    /**
     * 요청 정보의 유효성을 검증하는 메서드.
     * 이 메서드는 요청 정보가 유효하지 않은 경우 true를 반환하고, 유효한 경우 false를 반환해야 한다.
     *
     * @return 정보가 유효하지 않은 경우 true, 유효한 경우 false
     */
    boolean isInvalid();
}
