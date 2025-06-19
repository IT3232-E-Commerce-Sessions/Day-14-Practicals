package com.system.icae02.service;

import java.awt.print.Book;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.icae02.model.Borrow;
import com.system.icae02.model.Student;
import com.system.icae02.repository.BookRepository;
import com.system.icae02.repository.BorrowRepository;

@Service
public class BorrowService {
    private final BookService bookService;
    private final StudentService studentService;
    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    
    @Autowired
    public BorrowService(BookService bookService, StudentService studentService, 
                        BorrowRepository borrowRepository, BookRepository bookRepository) {
        this.bookService = bookService;
        this.studentService = studentService;
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
    }
    
    public Borrow borrowBook(String studentId, String bookId) {

        Student student = studentService.getStudentById(studentId);
        Book book = bookService.getBookById(bookId);

        if (!studentService.canBorrowMoreBooks(studentId)) {
            throw new IllegalStateException("Student has reached the borrowing limit of 2 unreturned books");
        }

        int availableCopies = bookService.getAvailableCopies(bookId);

        if (availableCopies <= 1) {
            throw new IllegalStateException(
                "Insufficient copies available. Library must maintain at least one copy not for lending"
            );
        }
        
        // Create borrow record
        Borrow borrow = new Borrow();
        borrow.setStudent(student);
        borrow.setBook(book);
        borrow.setBorrowDate(new Date());
        
        return borrowRepository.save(borrow);
    }
}