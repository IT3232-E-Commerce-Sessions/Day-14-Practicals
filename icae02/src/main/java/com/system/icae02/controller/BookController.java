package com.system.icae02.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.icae02.model.Student;
import com.system.icae02.service.BookService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public ResponseEntity<?> getBooksByGenre(
            @RequestParam(required = false) String genre) {
        if (genre != null) {
            return ResponseEntity.ok(bookService.getBooksByGenre(genre));
        }
        return ResponseEntity.ok(bookRepository.findAll());
    }
    
    @GetMapping("/{bookId}/borrowers")
    public ResponseEntity<?> getBookBorrowers(
            @PathVariable String bookId) {
        try {
            List<Student> borrowers = bookService.getStudentsByBookId(bookId);
            return ResponseEntity.ok(borrowers);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage()));
        }
    }
}