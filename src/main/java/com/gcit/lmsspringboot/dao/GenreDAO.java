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

import com.gcit.lmsspringboot.entity.Genre;
import com.gcit.lmsspringboot.entity.Book;

/**
 * @genre iratusmachina
 *
 */
@Component
public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{
	
	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException{
		mySqlTemplate.update("insert into tbl_genre(genre_name) values(?)", 
				genre.getGenreName());
	}
	
	public Integer addGenreWithID(Genre genre) throws ClassNotFoundException, SQLException {
		String insertSql = "insert into tbl_genre(genre_name) values(?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		String id_column = "genre_id";
		mySqlTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(insertSql, new String[] { id_column });
			ps.setString(1, genre.getGenreName());
			return ps;
		}, keyHolder);

		BigDecimal id = (BigDecimal) keyHolder.getKeys().get(id_column);
		return id.intValue();
	}
	
	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException{
		mySqlTemplate.update("delete from tbl_genre where genre_id = ?", 
				genre.getGenre_id());
	}
	
	public void editGenre(Genre genre) throws ClassNotFoundException, SQLException {
		mySqlTemplate.update("update tbl_genre set genre_name = ? where genre_id = ?", 
				genre.getGenreName(), genre.getGenre_id());
	}
	
	public List<Genre> getAllGenres() throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_genre", this);
	}
	
	public Genre getGenre(Genre genre) throws ClassNotFoundException, SQLException{
		return mySqlTemplate.query("select * from tbl_genre where genre_id = ?", 
				new Object[] {genre.getGenre_id()}, this).get(0);
	}
	
	public List<Genre> getAllGenresForABook(Book book) throws ClassNotFoundException, SQLException {
		return mySqlTemplate.query("select * from tbl_genre tg, tbl_book_genres tbg where tg.genre_id = tbg.genre_Id and tbg.bookId = ?", 
				new Object[] {book.getBookId()}, this);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreName(rs.getString("genre_name"));
			genre.setGenre_id(rs.getInt("genre_id"));
			genres.add(genre);
		}
		return genres;
	}

}
