package com.example.dailymanager.repository;

import com.example.dailymanager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByEventId(Long eventId);
}
