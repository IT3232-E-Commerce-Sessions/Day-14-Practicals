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
    @Autowired
    public BookRepository bookRepository;

    // Get book by genre
    public List<Book> getBooksByGenre(String genre){
        if(bookRepository.getByGenre(genre).isEmpty()){
            throw new EntityNotFoundException("Books Not Found for mentioned Genre");
        }
        return bookRepository.getByGenre(genre);
    }
}