package com.system.icae02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.icae02.model.Student;
import com.system.icae02.repository.BorrowRepository;
import com.system.icae02.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {
    @Autowired
    public StudentRepository studentRepository;

    public List<Student> getBorrowedStudent(String bookId){
        if(studentRepository.getBorrowedStudent(bookId).isEmpty()){
            throw new EntityNotFoundException("Students Not Found");
        }
        return studentRepository.getBorrowedStudent(bookId);
    }
}