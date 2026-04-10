package com.example.dailymanager.repository;

import com.example.dailymanager.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByOrderByUpdatedDateDesc();

    List<Event> findByAuthorOrderByUpdatedDateDesc(String author);
}
