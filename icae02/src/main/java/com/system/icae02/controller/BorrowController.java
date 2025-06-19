package com.system.icae02.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.icae02.model.Borrow;
import com.system.icae02.service.BorrowService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/borrow")
public class BorrowController {
    private final BorrowService borrowService;
    
    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }
    
    @PostMapping
    public ResponseEntity<?> borrowBook(
            @RequestBody Map<String, String> request) {
        try {
            String studentId = request.get("studentId");
            String bookId = request.get("bookId");
            
            if (studentId == null || bookId == null) {
                throw new IllegalArgumentException(
                    "Both studentId and bookId are required in the request body"
                );
            }
            
            Borrow borrow = borrowService.borrowBook(studentId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED).body(borrow);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage()));
        } catch (IllegalStateException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, ex.getMessage()));
        }
    }
}