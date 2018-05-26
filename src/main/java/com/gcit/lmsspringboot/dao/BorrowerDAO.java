/**
 * 
 */
package com.gcit.lmsspringboot.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import com.gcit.lmsspringboot.entity.Borrower;

/**
 * @author iratusmachina
 *
 */
@Component
public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{

	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException{
		mySqlTemplate.update("insert into tbl_borrower(name, address, phone) values(?, ?, ?)", 
				borrower.getName(), 
				borrower.getAddress(), 
				borrower.getPhone());
	}
	
	public Integer addBorrowerWithID(Borrower borrower) throws ClassNotFoundException, SQLException {
		String insertSql = "insert into tbl_borrower(name, address, phone) values(?, ?, ?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		String id_column = "cardNo";
		mySqlTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(insertSql, new String[] { id_column });
			ps.setString(1, borrower.getName());
			ps.setString(2, borrower.getAddress());
			ps.setString(3, borrower.getPhone());
			return ps;
		}, keyHolder);

		BigDecimal id = (BigDecimal) keyHolder.getKeys().get(id_column);
		return id.intValue();
	}
	
	public void updateBorrowerName(Borrower borrower) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("update tbl_borrower set name = ? where cardNo = ?", 
				borrower.getName(), 
				borrower.getCardNo());
	}
	
	public void updateBorrowerAddress(Borrower borrower) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("update tbl_borrower set address = ? where cardNo = ?", 
				borrower.getAddress(),
				borrower.getCardNo());
	}
	
	public void updateBorrowerPhone(Borrower borrower) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("update tbl_borrower set phone = ? where cardNo = ?", 
				borrower.getPhone(),
				borrower.getCardNo());
	}
	
	public void deleteBorrower(int cardNo) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("delete from tbl_borrower where cardNo = ?", cardNo);
	}
	
	public List<Borrower> getAllBorrowers() throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_borrower", this);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setAddress(rs.getString("address"));
			borrower.setName(rs.getString("name"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		return borrowers;
	}
}
