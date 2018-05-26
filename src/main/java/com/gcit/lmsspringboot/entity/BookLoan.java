/**
 * 
 */
package com.gcit.lmsspringboot.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author iratusmachina
 *
 */
public class BookLoan {
	private int bookId, branchId, cardNo;
	private LocalDateTime dateOut, dateIn, dueDate;
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public LocalDateTime getDateOut() {
		return dateOut;
	}
	public void setDateOut(LocalDateTime dateOut) {
		this.dateOut = dateOut;
	}
	public LocalDateTime getDateIn() {
		return dateIn;
	}
	public void setDateIn(LocalDateTime dateIn) {
		this.dateIn = dateIn;
	}
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("BookId: " + this.getBookId());
		sb.append("CardNo: " + this.getCardNo());
		sb.append("BranchId: " + this.getBranchId());
		sb.append("DueDate: " + this.getDueDate().format(formatter));
		sb.append("DateOut: " + this.getDateOut().format(formatter));
		if (this.getDateIn() != null) {
			sb.append("DateIn: " + this.getDateIn().format(formatter));
		}
		sb.append(System.lineSeparator());
		return sb.toString();
	}
}
