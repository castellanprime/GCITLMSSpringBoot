/**
 * 
 */
package com.gcit.lmsspringboot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lmsspringboot.entity.BookLoan;

/**
 * @author iratusmachina
 *
 */
@Component
public class BookLoanDAO extends BaseDAO<BookLoan> implements ResultSetExtractor<List<BookLoan>> {
	
	public void addLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		mySqlTemplate.update("insert into tbl_book_loans(bookId, branchId, cardNo, dateOut, dueDate, dateIn) values(?, ?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), NULL)", 
				bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo());
	}
	
	public void returnLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		mySqlTemplate.update("update tbl_book_loans set dateIn = NOW() where bookId = ? and branchId = ? and cardNo = ? and dateOut = ? and dateIn IS NULL", 
				bookLoan.getBookId(),bookLoan.getBranchId(),bookLoan.getCardNo(),bookLoan.getDateOut());
	}
	

	public List<BookLoan> getCurrentLoans(BookLoan bookLoan) throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_book_loans where branchId = ? and bookId = ? and cardNo = ? and dateIn is NULL", 
				new Object[] { bookLoan.getBranchId(), bookLoan.getBookId(), bookLoan.getCardNo() },this);
	}
	
	public List<BookLoan> getCurrentLoansForBranch(int branchId) throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_book_loans where branchId = ? and dateIn is NULL", 
				new Object[] { branchId },this);
	}
	
	public List<BookLoan> getAllBookLoansForBranch(int branchId) throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_book_loans where branchId = ?", 
				new Object[] { branchId }, this);
	}
	
	public void changeDueDate(BookLoan bookLoan) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		mySqlTemplate.update("update tbl_book_loans set dueDate = ? where bookId = ? and branchId = ? and cardNo = ? and dateOut = ?", 
				bookLoan.getDueDate(), bookLoan.getBookId(), bookLoan.getBranchId(), 
				bookLoan.getCardNo(), bookLoan.getDateOut());
	}
	
	private LocalDateTime getTimeFromTimeStamp(Timestamp t) {
		return LocalDateTime.ofInstant(t.toInstant(), ZoneId.systemDefault());
	}

	@Override
	public List<BookLoan> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<BookLoan> bookLoans = new ArrayList<>();
		while(rs.next()) {
			BookLoan bkl = new BookLoan();
			bkl.setBookId(rs.getInt("bookId"));
			bkl.setBranchId(rs.getInt("branchId"));
			bkl.setCardNo(rs.getInt("cardNo"));
			Timestamp t = rs.getTimestamp("dateOut");
			bkl.setDateOut(this.getTimeFromTimeStamp(t));
			t = rs.getTimestamp("dueDate");
			bkl.setDueDate(this.getTimeFromTimeStamp(t));
			t = rs.getTimestamp("dateIn");
			if (rs.wasNull() == false) {
				bkl.setDateIn(this.getTimeFromTimeStamp(t));
			}
			bookLoans.add(bkl);
		}
		return bookLoans;
	}
}
