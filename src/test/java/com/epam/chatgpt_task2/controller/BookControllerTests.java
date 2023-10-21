package com.epam.chatgpt_task2.controller;

import com.epam.chatgpt_task2.model.Author;
import com.epam.chatgpt_task2.model.Book;
import com.epam.chatgpt_task2.model.Genre;
import com.epam.chatgpt_task2.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookControllerTests {

    private BookController bookController;
    private BookRepository bookRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookController = new BookController(bookRepository);
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() {
        List<Book> mockBooks = List.of(
                new Book(1L, "Book 1", new Author(1L, "Author 1 firstName", "Author 1 lastName"), new Genre(1L, "Genre 1"), 19.99, 50),
                new Book(2L, "Book 2", new Author(2L, "Author 2 firstName", "Author 2 lastName"), new Genre(2L, "Genre 2"), 12.99, 30)
        );

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> response = bookController.getAllBooks();

        assertEquals(mockBooks, response);
    }

    @Test
    public void testGetBookById() {
        long bookId = 1L;
        Book mockBook = new Book(bookId, "Book 1", new Author(1L, "Author 1 firstName", "Author 1 lastName"), new Genre(1L, "Genre 1"), 19.99, 50);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        ResponseEntity<Book> response = bookController.getBookById(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBook, response.getBody());
    }

    @Test
    public void testGetBookById_NotFound() {
        long bookId = 3L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.getBookById(bookId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testCreateBook() {
        Book newBook = new Book(null, "New Book", new Author(1L, "Author 1 firstName", "Author 1 lastName"), new Genre(1L, "Genre 1"), 14.99, 20);

        when(bookRepository.save(newBook)).thenReturn(newBook);

        ResponseEntity<Book> response = bookController.createBook(newBook);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newBook, response.getBody());
    }

    @Test
    public void testUpdateBook() {
        long bookId = 1L;
        Book updatedBook = new Book(bookId, "Updated Book", new Author(1L, "Author 1 firstName", "Author 1 lastName"), new Genre(1L, "Genre 1"), 15.99, 25);

        when(bookRepository.findById(any())).thenReturn(Optional.of(updatedBook));

        ResponseEntity<Book> response = bookController.updateBook(bookId, updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBook, response.getBody());
    }

    @Test
    public void testDeleteBook() {
        long bookId = 2L;

        // Make sure that bookRepository.existsById(2L) returns true
        when(bookRepository.existsById(bookId)).thenReturn(true);

        ResponseEntity<Void> response = bookController.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testUpdateBook_NotFound() {
        // Create a book that doesn't exist in the repository
        long bookId = 1L;
        Book nonExistentBook = new Book(bookId, "Updated Book", new Author(1L, "Author 1 firstName", "Author 1 lastName"), new Genre(1L, "Genre 1"), 15.99, 25);

        // Set up a scenario where the book doesn't exist
        when(bookRepository.findById(100L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.updateBook(100L, nonExistentBook);

        // Verify that the response status is NOT FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteBook_NotFound() {
        long nonExistentBookId = 100L;

        // Set up a scenario where the book doesn't exist
        when(bookRepository.existsById(nonExistentBookId)).thenReturn(false);

        ResponseEntity<Void> response = bookController.deleteBook(nonExistentBookId);

        // Verify that the response status is NOT FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchByTitle() throws Exception {
        String title = "Sample Book Title";

        // Create a list of books with the specified title
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book", new Author(1L, "Author 1 firstName", "Author 1 lastName"), new Genre(1L, "Genre 1"), 15.99, 25));

        // Mock the behavior of the repository to return the list of books
        when(bookRepository.findByTitleContaining(title)).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/searchByTitle")
                                              .param("title", title))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(books.size()));
    }

    @Test
    public void testSearchByAuthor() throws Exception {
        String author = "Sample Author";

        // Create a list of books by the specified author
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", new Author(1L, "Author 1 firstName", "Sample Author"), new Genre(1L, "Genre 1"), 15.99, 25));
        books.add(new Book(2L, "Book 2", new Author(2L, "Author 2 firstName", "Sample Author"), new Genre(1L, "Genre 2"), 15.99, 25));

        // Mock the behavior of the repository to return the list of books
        when(bookRepository.findByAuthorLastNameContaining(author)).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/searchByAuthor")
                                              .param("authorLastName", author))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(books.size()));
    }


    @Test
    public void testSearchByGenre() throws Exception {
        String genre = "Sample Genre";

        // Create a list of books with the specified genre
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", new Author(1L, "Author 1 firstName", "Sample Author"), new Genre(1L, "Genre 1"), 15.99, 25));

        // Mock the behavior of the repository to return the list of books
        when(bookRepository.findByGenreNameContaining(genre)).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/searchByGenre")
                                              .param("genre", genre))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(books.size()));
    }
}