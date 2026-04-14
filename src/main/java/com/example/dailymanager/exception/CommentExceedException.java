package com.example.dailymanager.exception;

/**
 * 일정의 댓글이 10개 초과인 경우 발생하는 예외 클래스. 일정에 달린 댓글이 최대 개수를 초과할 때 이 예외가 발생한다.
 * Transactional을 사용하는 관계로 반드시 RuntimeException을 상속받아 예외 발생시 롤백이 되도록 하였다.
 */
public class CommentExceedException extends RuntimeException {
    public CommentExceedException() {
        super("Comments exceeded for this event");
    }
}
