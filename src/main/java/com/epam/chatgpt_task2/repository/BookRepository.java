package com.epam.chatgpt_task2.repository;

import com.epam.chatgpt_task2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorLastNameContaining(String lastName);
    List<Book> findByGenreNameContaining(String genreName);
}