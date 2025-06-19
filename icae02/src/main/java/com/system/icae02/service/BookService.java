package com.system.icae02.service;

import java.awt.print.Book;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.icae02.model.Student;
import com.system.icae02.repository.BookRepository;
import com.system.icae02.repository.BorrowRepository;
import com.system.icae02.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;
    private final StudentRepository studentRepository;
    
    @Autowired
    public BookService(BookRepository bookRepository, BorrowRepository borrowRepository, StudentRepository studentRepository) {
        this.bookRepository = bookRepository;
        this.borrowRepository = borrowRepository;
        this.studentRepository=studentRepository;
    }
    
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }
    
    public Book getBookById(String id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + id));
    }
    
    public List<Student> getStudentsByBookId(String bookId) {
        return studentRepository.findStudentsByBookId(bookId);
    }
    
    public int getAvailableCopies(String bookId) {
        Book book = getBookById(bookId);
        int borrowedCopies = borrowRepository.countBorrowedCopies(bookId);
        return book.getCopiesAvailable() - borrowedCopies;
    }
}