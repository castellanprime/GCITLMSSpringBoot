package com.gcit.lmsspringboot.entity;

import java.time.LocalDateTime;

public class BookLoanDTO {
	private Book book;
	private LibraryBranch branch;
	private Borrower borrower;
	private LocalDateTime dateOut, dueDate, dateIn;
	
	public LibraryBranch getBranch() {
		return branch;
	}
	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	public Borrower getBorrower() {
		return borrower;
	}
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
	public LocalDateTime getDateOut() {
		return dateOut;
	}
	public void setDateOut(LocalDateTime dateOut) {
		this.dateOut = dateOut;
	}
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public LocalDateTime getDateIn() {
		return dateIn;
	}
	public void setDateIn(LocalDateTime dateIn) {
		this.dateIn = dateIn;
	}
	
}
