package com.system.icae02.controller;

import java.awt.print.Book;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.icae02.service.BookService;


@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    public BookService bookService;

    @GetMapping("/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable("genre") String genre){
        return new ResponseEntity<List<Book>>(bookService.getBooksByGenre(genre),HttpStatus.OK); 
    }
}