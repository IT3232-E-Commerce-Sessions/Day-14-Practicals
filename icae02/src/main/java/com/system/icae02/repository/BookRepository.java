package com.system.icae02.repository;

import java.awt.print.Book;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
	// Filter Books by genre
    @Query("SELECT b from Book b WHERE b.genre=?1")
    public List<Book> getByGenre(String genre);
}