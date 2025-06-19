package com.system.icae02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.icae02.model.Student;
import com.system.icae02.repository.BorrowRepository;
import com.system.icae02.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final BorrowRepository borrowRepository;
    
    @Autowired
    public StudentService(StudentRepository studentRepository, BorrowRepository borrowRepository) {
        this.studentRepository = studentRepository;
        this.borrowRepository = borrowRepository;
    }
    
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));
    }
    
    public boolean canBorrowMoreBooks(String studentId) {
        int unreturnedCount = borrowRepository.countUnreturnedBooks(studentId);
        return unreturnedCount < 2;
    }
}