package com.epam.chatgpt_task2.repository;

import com.epam.chatgpt_task2.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    // You can define custom query methods here if needed
}