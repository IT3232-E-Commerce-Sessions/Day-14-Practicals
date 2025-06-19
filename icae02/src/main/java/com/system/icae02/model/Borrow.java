package com.system.icae02.model;
import java.sql.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Borrow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Student student;
	@ManyToOne
	private Book book;
	//@Column(name="borrow_date",columnDefinition = "date default CURRENT_DATE")
	private Date borrowDate=new Date(System.currentTimeMillis());
	@ColumnDefault("null")
	private Date returnDate;	
	public enum Status {
	    NO,
	    YES
	}
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status returned=Status.NO;
	
	public Borrow() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Status getReturned() {
		return returned;
	}

	public void setReturned(Status returned) {
		this.returned = returned;
	}
	
}