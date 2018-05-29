package com.gcit.lmsspringboot;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gcit.lmsspringboot.dao.AuthorDAO;
import com.gcit.lmsspringboot.dao.LibraryBranchDAO;
import com.gcit.lmsspringboot.entity.Author;
import com.gcit.lmsspringboot.entity.LibraryBranch;
import com.gcit.lmsspringboot.service.AdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LmsspringbootApplicationTests {
	/**
	@Test
	public void contextLoads() {
	}**/
	
	@Autowired
	LibraryBranchDAO libraryBranchDao;
	
	@Autowired
	LibraryBranch branch;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	AuthorDAO authorDAO;
	
	@Test
	public void testAuthorInsert() throws SQLException, ClassNotFoundException{
		Author author = new Author();
		author.setAuthorName("Gavin Crowder");
		int authorId = authorDAO.addAuthorWithID(author);
		System.out.println(authorId);
		assertNotNull(authorId);
	}
	
	@Test
	public void testLibraryBranchInsert() throws SQLException, ClassNotFoundException{
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchName("University Library");
		branch.setBranchAddress("Cleveland, Ohio");
		int branchId = libraryBranchDao.addBranchWithID(branch);
		System.out.println(branchId);
		assertNotNull(branchId);
	}
}
