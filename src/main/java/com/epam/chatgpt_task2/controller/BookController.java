package com.epam.chatgpt_task2.controller;

import com.epam.chatgpt_task2.model.Book;
import com.epam.chatgpt_task2.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookRepository.save(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // Read a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Update a book by ID
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            // Update fields as needed
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setGenre(updatedBook.getGenre());
            book.setPrice(updatedBook.getPrice());
            book.setQuantityAvailable(updatedBook.getQuantityAvailable());
            bookRepository.save(book);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Search books by title
    @GetMapping("/searchByTitle")
    public ResponseEntity<List<Book>> searchByTitle(@RequestParam String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Search books by author
    @GetMapping("/searchByAuthor")
    public ResponseEntity<List<Book>> searchByAuthor(@RequestParam String authorLastName) {
        List<Book> books = bookRepository.findByAuthorLastNameContaining(authorLastName);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Search books by genre
    @GetMapping("/searchByGenre")
    public ResponseEntity<List<Book>> searchByGenre(@RequestParam String genre) {
        List<Book> books = bookRepository.findByGenreNameContaining(genre);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
