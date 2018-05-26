/**
 * 
 */
package com.gcit.lmsspringboot.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
/**
 * @author iratusmachina
 *
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import com.gcit.lmsspringboot.entity.Author;
import com.gcit.lmsspringboot.entity.Book;

/**
 * @author iratusmachina
 *
 */
@Component
public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>>{

	public void addAuthor(Author author) throws ClassNotFoundException, SQLException{
		mySqlTemplate.update("insert into tbl_author(authorName) values(?)", author.getAuthorName());
	}
	
	public Integer addAuthorWithID(Author author) throws ClassNotFoundException, SQLException {
		String insertSql = "insert into tbl_author(authorName) values(?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		String id_column = "authorId";
		mySqlTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(insertSql, new String[] { id_column });
			ps.setString(1, author.getAuthorName());
			return ps;
		}, keyHolder);

		BigDecimal id = (BigDecimal) keyHolder.getKeys().get(id_column);
		return id.intValue();
	}
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		mySqlTemplate.update("delete from tbl_author where authorId = ?", author.getAuthorId());
	}
	
	public void editAuthor(Author author) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		mySqlTemplate.update("update tbl_author set authorName = ? where authorId = ?", 
				author.getAuthorName(), 
				author.getAuthorId());
	}
	
	public List<Author> getAllAuthors() throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_author", this);
	}
	
	public List<Author> getAllAuthorsForABook(Book book) throws ClassNotFoundException, SQLException {
		return mySqlTemplate.query("select tba.authorId, ta.authorName from tbl_book_authors tba, tbl_author ta where tba.authorId = ta.authorId and tba.bookId = ?", 
					new Object[] {book.getBookId()}, 
					this);
	}
	
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorName(rs.getString("authorName"));
			author.setAuthorId(rs.getInt("authorId"));
			authors.add(author);
		}
		return authors;
	}
}

