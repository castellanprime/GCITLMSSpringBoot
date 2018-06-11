/**
 * 
 */
package com.gcit.lmsspringboot.entity;

import java.util.List;

/**
 * @author iratusmachina
 *
 */

public class Borrower {
	private int cardNo;
	private String name, address, phone;
	private List<BookLoanDTO> allBookLoans;
	private List<BookLoanDTO> currentBookLoans;
	
	public List<BookLoanDTO> getAllBookLoans() {
		return allBookLoans;
	}
	public void setAllBookLoans(List<BookLoanDTO> allBookLoans) {
		this.allBookLoans = allBookLoans;
	}
	public List<BookLoanDTO> getCurrentBookLoans() {
		return currentBookLoans;
	}
	public void setCurrentBookLoans(List<BookLoanDTO> currentBookLoans) {
		this.currentBookLoans = currentBookLoans;
	}

	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
