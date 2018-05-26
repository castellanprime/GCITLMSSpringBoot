/**
 * 
 */
package com.gcit.lmsspringboot.entity;


/**
 * @author iratusmachina
 *
 */

public class Genre {
	private int genre_id;
	private String genre_name;
	
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
