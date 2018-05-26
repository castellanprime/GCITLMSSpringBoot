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

/**
 * @author iratusmachina
 *
 */

import com.gcit.lmsspringboot.entity.LibraryBranch;

@Component
public class LibraryBranchDAO extends BaseDAO<LibraryBranch> 
	implements ResultSetExtractor<List<LibraryBranch>>{

	public void addBranch(LibraryBranch lb) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("insert into tbl_library_branch(branchName, branchAddress) values(?, ?)", 
				lb.getBranchName(), lb.getBranchAddress());
	}
	
	public Integer addBranchWithID(LibraryBranch lb) throws ClassNotFoundException, SQLException {
		String insertSql = "insert into tbl_library_branch(branchName, branchAddress) values(?, ?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		String id_column = "branchId";
		mySqlTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(insertSql, new String[] { id_column });
			ps.setString(1, lb.getBranchName());
			ps.setString(2, lb.getBranchAddress());
			return ps;
		}, keyHolder);

		BigDecimal id = (BigDecimal) keyHolder.getKeys().get(id_column);
		return id.intValue();
	}
	
	public void updateBranchName(LibraryBranch lb, String branchName) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("update tbl_library_branch set branchName = ? where branchId = ?", 
				branchName, lb.getBranchId());
	}
	
	public void updateBranchAddress(LibraryBranch lb, String branchAddress) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("update tbl_library_branch set branchAddress = ? where branchId = ?", 
				branchAddress, lb.getBranchId());
	}
	
	public void deleteBranch(LibraryBranch lb) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("delete from tbl_library_branch where branchId = ?", 
				lb.getBranchId());
	}

	public List<LibraryBranch> getAllBranches() throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_library_branch", this);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<LibraryBranch> branches = new ArrayList<>();
		while(rs.next()) {
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchAddress(rs.getString("branchAddress"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchId(rs.getInt("branchId"));
			branches.add(branch);
		}
		return branches;
	}
}
