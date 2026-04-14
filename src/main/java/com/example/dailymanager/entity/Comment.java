package com.example.dailymanager.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 댓글 엔티티
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Column(nullable = false)
    private Long eventId;

    protected Comment() {
    }

    public Comment (String content, String author, String password, long eventId) {
        this.content = content;
        this.author = author;
        this.password = password;
        this.eventId = eventId;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getUpdatedDate() {
        return updatedDate.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    public Long getEventId() {
        return eventId;
    }



}
