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

import com.gcit.lmsspringboot.entity.Author;
import com.gcit.lmsspringboot.entity.Book;

/**
 * @author iratusmachina
 *
 */
@Component
public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>> {
	
	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("insert into tbl_book (title) values (?)", new Object[] { book.getTitle() });
	}
	
	public Integer addBookWithID(Book book) throws ClassNotFoundException, SQLException {
		String insertSql = "insert into tbl_book (title) values (?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		String id_column = "bookId";
		mySqlTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(insertSql, new String[] { id_column });
			ps.setString(1, book.getTitle());
			ps.setInt(2, book.getPublisher().getPublisherId());
			return ps;
		}, keyHolder);

		BigDecimal id = (BigDecimal) keyHolder.getKeys().get(id_column);
		return id.intValue();
	}

	public void addBookAuthors(Book book, int authorId) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("insert into tbl_book_authors values (?, ?)", 
				new Object[] { book.getBookId(), authorId });
	}

	public List<Book> readBooksByAuthorId(Author author) {
		return mySqlTemplate.query(
				"select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId =?)",
				new Object[] { author.getAuthorId() }, this);
	}

	public void addBookGenres(Book book, int genreId) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("insert into tbl_book_genres values (?, ?)", 
				new Object[] { genreId, book.getBookId() });
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("update tbl_book set title = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
	}
	
	public void updateBookPublisher(Book book) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("update tbl_book set publisher = ? where bookId = ?",
				new Object[] { book.getPublisher().getPublisherId(), book.getBookId() });
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("delete from tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
		return mySqlTemplate.query("select * from tbl_book", this);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			books.add(book);
		}
		return books;
	}
}
