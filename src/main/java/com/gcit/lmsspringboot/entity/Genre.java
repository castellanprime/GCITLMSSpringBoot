/**
 * 
 */
package com.gcit.lmsspringboot.entity;

import java.util.List;

/**
 * @author iratusmachina
 *
 */

public class Genre {
	private int genre_id;
	private String genre_name;
	private List<Book> books;
	
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public int getGenre_id() {
		return genre_id;
	}
	public void setGenre_id(int genre_id) {
		this.genre_id = genre_id;
	}
	public String getGenreName() {
		return genre_name;
	}
	public void setGenreName(String genre_name) {
		this.genre_name = genre_name;
	}
}
