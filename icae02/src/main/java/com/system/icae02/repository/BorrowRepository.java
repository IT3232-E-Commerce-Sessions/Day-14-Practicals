package com.system.icae02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.system.icae02.model.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.student.id = :studentId AND b.returned = 'NO'")
    int countUnreturnedBooks(@Param("studentId") String studentId);
    
    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.book.id = :bookId AND b.returned = 'NO'")
    int countBorrowedCopies(@Param("bookId") String bookId);
}