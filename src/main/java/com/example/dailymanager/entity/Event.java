package com.example.dailymanager.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Event (String title, String description, String author, String password) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.password = password;
    }

    public long getId() {

        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
    public String getAuthor() {
        return this.author;
    }
    public String getUpdatedDate() {


        return updatedDate.format(DateTimeFormatter.BASIC_ISO_DATE);
    }
}
