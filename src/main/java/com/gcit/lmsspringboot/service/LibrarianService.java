package com.gcit.lmsspringboot.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lmsspringboot.dao.BookDAO;
import com.gcit.lmsspringboot.entity.Book;
import com.gcit.lmsspringboot.entity.LibraryBookCopies;
import com.gcit.lmsspringboot.entity.LibraryBranch;
import com.gcit.lmsspringboot.dao.LibraryBookCopiesDAO;
import com.gcit.lmsspringboot.dao.LibraryBranchDAO;

@RestController
@RequestMapping("/lmsspringboot/")
public class LibrarianService {

	@Autowired
	LibraryBranchDAO lbdao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	LibraryBookCopiesDAO lbcdao;
	
	private LibraryBranch getBranchByID(List<LibraryBranch> branches, int branchId) {
		for (LibraryBranch libraryBranch: branches) {
			if (libraryBranch.getBranchId() == branchId) {
				return libraryBranch;
			}
		}
		return null;
	}
	
	private Book getBookById(List<Book> books, int bookId) {
		for (Book book: books) {
			if (book.getBookId() == bookId) {
				return book;
			}
		}
		return null;
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/librarian/branches/{branchId}", 
		method = RequestMethod.PATCH, 
		produces = "application/json")
	public LibraryBranch editBranch(@PathVariable int branchId, 
			@RequestParam(value="name", required=false) String branchName,
			@RequestParam(value="address", required=false) String branchAddress)
			throws SQLException {
		LibraryBranch branch = null;
		try {
			List<LibraryBranch> branches = lbdao.getAllBranches();
			LibraryBranch libraryBranch = this.getBranchByID(branches, branchId);
			if (branchName != null && branchName.trim().length() != 0) {
				lbdao.updateBranchName(libraryBranch, branchName);
			}
			if (branchAddress != null && branchAddress.trim().length() != 0) {
				lbdao.updateBranchAddress(libraryBranch, branchAddress);
			}
			branches = lbdao.getAllBranches();
			branch = this.getBranchByID(branches, branchId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return branch;
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/librarian/branches/{branchId}/books/{bookId}/copies", 
		method = RequestMethod.PATCH, 
		produces = "application/json")
	public LibraryBookCopies updateCopiesOfBookInBranch(@RequestBody LibraryBookCopies lbc) throws SQLException{
		LibraryBookCopies lbco = null;
		try {
			//List<LibraryBookCopies> libraryBookBranches = lbcdao.getAllCopiesOfBookInBranch(branchId, bookId);
			//LibraryBookCopies lbc = libraryBookBranches.get(0);
			//lbc.setNoOfCopies(copies);
			lbcdao.updateBookCopies(lbc);
			lbco = lbcdao.getAllCopiesOfBookInBranch(lbc.getBranchId(), lbc.getBookId()).get(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return lbco;
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/librarian/branches/{branchId}/books", 
			method = RequestMethod.POST, 
			produces = "application/json")
	public LibraryBookCopies addNewBookToBranch(@RequestBody LibraryBookCopies lbc) throws SQLException{
		LibraryBookCopies lbco = new LibraryBookCopies();
		try {
			lbcdao.saveBranchBook(lbc);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return lbco;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/librarian/branches/books", 
		method = RequestMethod.GET, 
		produces = "application/json")
	public List<Book> getAllBooksToAddToBranch() throws SQLException{
		List<Book> lb = new ArrayList<>();
		try {
			lb = bdao.readAllBooks();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return lb;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/librarian/branches/{branchId}/books/{bookId}/copies", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public LibraryBookCopies getCopiesOfBookInBranch(@PathVariable int branchId, 
			@PathVariable int bookId) throws SQLException{
		LibraryBookCopies lbco = null;
		try {
			List<LibraryBookCopies> libraryBookBranches = lbcdao.getAllCopiesOfBookInBranch(branchId, bookId);
			if (libraryBookBranches == null || libraryBookBranches.size() == 0) {
				lbco = new LibraryBookCopies();
				lbco.setNoOfCopies(0);
			} else {
				lbco = libraryBookBranches.get(0);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return lbco;
	}
	
	/**
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/librarian/branches/{branchId}/books", 
			method = RequestMethod.GET, 
			consumes = "application/json",
			produces = "application/json")
	public List<Book> getAllBooksInBranch(@PathVariable int branchId,
			@RequestParam Boolean available) throws SQLException {
		List<Book> books = new ArrayList<>();
		try {
			List<LibraryBookCopies> allBooks = lbcdao.getAllBooksForABranch(branchId);
			List<Book> allBooksInDatabase = bdao.readAllBooks();
			if (available != null && available == false) {
				for (LibraryBookCopies lbc: allBooks) {
					Book book = this.getBookById(allBooksInDatabase, lbc.getBookId());
					books.add(book);
				}
			} else if (available != null && available == true) {
				for (LibraryBookCopies lbc: allBooks) {
					if (lbc.getNoOfCopies() > 0) {
						Book book = this.getBookById(allBooksInDatabase, lbc.getBookId());
						books.add(book);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return books;
	}
	**/
	
}
