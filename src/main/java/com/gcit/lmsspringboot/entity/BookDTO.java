/**
 * 
 */
package com.gcit.lmsspringboot.entity;

/**
 * @author iratusmachina
 *
 */
public class BookDTO {
	private String title;
	private Publisher publisher;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
}
