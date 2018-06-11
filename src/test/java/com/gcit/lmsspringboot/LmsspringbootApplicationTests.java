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
	
	@Test
	public void contextLoads() {
	}
}
