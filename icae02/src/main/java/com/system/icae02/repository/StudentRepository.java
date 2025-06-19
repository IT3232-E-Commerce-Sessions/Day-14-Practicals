package com.system.icae02.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.system.icae02.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("SELECT s FROM Student s JOIN s.borrows b WHERE b.book.id = :bookId")
    List<Student> findStudentsByBookId(@Param("bookId") String bookId);
}