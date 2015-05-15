package com.karta.model;
 
public class ReviewModel {
	private String name;
	private String reviewsDate;
	private String comment;
	private double rating;
 
	public ReviewModel(String name, String comment, String reviewsDate, float rating) {
		this.name = name;
		this.comment = comment;
		this.reviewsDate = reviewsDate;
		this.rating = rating;
	}
 
	public String getName() {
		return this.name;
	}
	 
	public String getComment() {
		return this.comment;
	}
	
	public String getDate() {
		return this.reviewsDate;
	}
	
	public double getRating() {
		return this.rating;
	}
	
}