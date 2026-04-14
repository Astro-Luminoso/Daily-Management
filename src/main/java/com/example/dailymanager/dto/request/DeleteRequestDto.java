package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

/**
 * 삭제 요청을 위한 DTO 클래스. 삭제 요청 시 필요한 비밀번호를 포함한다.
 *
 * @param password 삭제하고자 하는 리소스의 비밀번호. 삭제 요청 시 이 비밀번호가 유효한지 검증된다.
 */
public record DeleteRequestDto(String password) implements Validatable {

    @Override
    public boolean isInvalid() {
        return password.isBlank();
    }

}
