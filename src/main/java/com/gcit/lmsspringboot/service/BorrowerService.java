package com.gcit.lmsspringboot.service;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gcit.lmsspringboot.dao.BookLoanDAO;
import com.gcit.lmsspringboot.dao.BorrowerDAO;
import com.gcit.lmsspringboot.entity.BookLoan;
import com.gcit.lmsspringboot.entity.Borrower;

@RestController
@RequestMapping("/lmsspringboot/")
public class BorrowerService {

	@Autowired
	BorrowerDAO bdao;
	
	@Autowired
	BookLoanDAO bldao;
	
	private Borrower getBorrowerById(List<Borrower> borrowers, int cardNo) {
		for (Borrower borrower: borrowers) {
			if (borrower.getCardNo() == cardNo) {
				return borrower;
			}
		}
		return null;
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/borrowers/{cardNo}/checkout", 
		method = RequestMethod.POST, 
		consumes = "application/json",
		produces = "application/json")
	public BookLoan checkOutBook(@RequestParam int branchId, 
			@RequestParam int bookId, @PathVariable int cardNo) 
			throws SQLException{
		BookLoan bookLoanToReturn = null;
		try {
			BookLoan bookLoan = new BookLoan();
			bookLoan.setBookId(bookId);
			bookLoan.setBranchId(branchId);
			bookLoan.setCardNo(cardNo);
			bldao.addLoan(bookLoan);
			bookLoanToReturn = bldao.getCurrentLoans(bookLoan).get(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return bookLoanToReturn;
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/borrowers/{cardNo}/return", 
		method = RequestMethod.PATCH, 
		consumes = "application/json")
	public BookLoan returnBook(@RequestParam int branchId, 
			@RequestParam int bookId, @PathVariable int cardNo) 
			throws SQLException{
		BookLoan bookLoan = null;
		try {
			List<BookLoan> bookLoans = bldao.getAllBookLoansForBranch(branchId);
			for (BookLoan bk: bookLoans) {
				if (bk.getBookId() == bookId && bk.getCardNo() == cardNo) {
					bldao.returnLoan(bk);
					break;
				}
			}
			bookLoans = bldao.getAllBookLoansForBranch(branchId);
			for (BookLoan bk: bookLoans) {
				if (bk.getBookId() == bookId && bk.getCardNo() == cardNo) {
					bookLoan = bk;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return bookLoan;
	}
	
	@Transactional
	@RequestMapping(value = "/borrowers", 
		method = RequestMethod.POST, 
		consumes = "application/json",
		produces = "application/json")
	public ResponseEntity<Borrower> addBorrower(@RequestParam String name, 
			@RequestParam String address, 
			@RequestParam String phone,
			UriComponentsBuilder ucb) 
			throws SQLException{
		Borrower borrowerToReturn = null;
		try {
			Borrower borrower= new Borrower();
			borrower.setName(name);
			borrower.setAddress(address);
			borrower.setPhone(phone);
			int borrowerId = bdao.addBorrowerWithID(borrower);
			List<Borrower> borrowers = bdao.getAllBorrowers();
			borrowerToReturn = this.getBorrowerById(borrowers, borrowerId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		URI locationUri =
				ucb.path("/borrowers/")
				.path(String.valueOf(borrowerToReturn.getCardNo()))
				.build()
				.toUri();
		headers.setLocation(locationUri);
		return new ResponseEntity<>(borrowerToReturn, headers, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/borrowers/{cardNo}", 
			method = RequestMethod.DELETE)
	public void deleteBorrower (@PathVariable int cardNo) throws SQLException {
		try {
			bdao.deleteBorrower(cardNo);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/borrowers", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public List<Borrower> getAllBorrowers() throws SQLException{
		List<Borrower> borrowers = new ArrayList<>();
		try {
			borrowers = bdao.getAllBorrowers();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return borrowers;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/borrowers/{cardNo}", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public Borrower getBorrower(@PathVariable int cardNo) 
			throws SQLException{
		Borrower borrower = null;
		try {
			List<Borrower> borrowers = bdao.getAllBorrowers();
			borrower = this.getBorrowerById(borrowers, cardNo);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return borrower;
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/borrowers/{cardNo}", 
			method = RequestMethod.PATCH,
			consumes="application/json",
			produces = "application/json")
	public Borrower editBorrower(@PathVariable int cardNo, 
			@RequestParam (value="name", required=false) String name, 
			@RequestParam (value="phone", required=false) String phone, 
			@RequestParam (value="address", required=false) String address) throws SQLException{
		Borrower borrower = null;
		try {
			List<Borrower> borrowers = bdao.getAllBorrowers();
			borrower = this.getBorrowerById(borrowers, cardNo);
			if (name != null && name.trim().length() != 0) {
				borrower.setName(name);
				bdao.updateBorrowerName(borrower);
			}
			if (phone != null && phone.trim().length() != 0) {
				borrower.setPhone(phone);
				bdao.updateBorrowerPhone(borrower);
			}
			if (address != null && address.trim().length() != 0) {
				borrower.setAddress(address);
				bdao.updateBorrowerAddress(borrower);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return borrower;
	}
}
