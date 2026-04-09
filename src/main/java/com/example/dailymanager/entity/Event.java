package com.example.dailymanager.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Event {

    @Id
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    protected Event() {}

    public Event (long id, String title, String description, String author, String password) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.password = password;
    }
}
