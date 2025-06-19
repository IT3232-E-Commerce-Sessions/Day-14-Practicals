package com.system.icae02.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.icae02.model.Student;
import com.system.icae02.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    public StudentService studentService;

    @GetMapping("/{bookId}")
    public ResponseEntity<List<Student>> getBorrowedStudents(@PathVariable("bookId") String bookId) {
        return new ResponseEntity<List<Student>>(studentService.getBorrowedStudent(bookId),HttpStatus.OK);
    }
}