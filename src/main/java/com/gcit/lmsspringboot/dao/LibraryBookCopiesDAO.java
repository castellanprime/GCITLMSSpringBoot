package com.gcit.lmsspringboot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lmsspringboot.entity.LibraryBookCopies;

/**
 * @author iratusmachina
 *
 */
@Component
public class LibraryBookCopiesDAO extends BaseDAO<LibraryBookCopies> 
	implements ResultSetExtractor<List<LibraryBookCopies>> {
	
	public void saveBranchBook(LibraryBookCopies lbc) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("insert into tbl_book_copies (bookId, branchId, noofCopies) values (?, ?, ?)",  
				lbc.getBookId(), lbc.getBranchId(), lbc.getNoOfCopies());
	}
	
	public void updateBookCopies(LibraryBookCopies lbc) throws ClassNotFoundException, SQLException{
		mySqlTemplate.update("update tbl_book_copies tbc set tbc.noOfCopies = ? where tbc.branchId = ? and tbc.bookId = ? ", 
				lbc.getNoOfCopies(), lbc.getBranchId(), lbc.getBookId());
	}
	
	public List<LibraryBookCopies> getAllBooksForABranch(int branchId) throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_book_copies tbc where tbc.branchId = ?", 
				new Object[] { branchId }, this);
	}
	
	public List<LibraryBookCopies> getAllCopiesOfBookInBranch(int branchId, int bookId) throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_book_copies tbc where tbc.branchId = ? and tbc.bookId = ?", 
				new Object[] { branchId, bookId }, 
				this);
	}

	@Override
	public List<LibraryBookCopies> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<LibraryBookCopies> lbcl = new ArrayList<LibraryBookCopies>();
		while (rs.next()) {
			LibraryBookCopies lbc = new LibraryBookCopies();
			lbc.setBookId(rs.getInt("bookId"));
			lbc.setBranchId(rs.getInt("branchId"));
			lbc.setNoOfCopies(rs.getInt("noOfCopies"));
			lbcl.add(lbc);
		}
		return lbcl;
	}
	
}
