package com.epam.chatgpt_task2.repository;

import com.epam.chatgpt_task2.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // You can define custom query methods here if needed
}
