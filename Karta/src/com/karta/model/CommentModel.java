package com.karta.model;

import java.util.Date;
 
public class CommentModel {
	private Integer id;
	private Date date;
	private String comment;
	private Double rating;
	private MenuModel menu;
	private UserModel user;
 
	public CommentModel(){}
	
	public CommentModel(Date date, String comment, MenuModel menu) {
		this.date = date;
		this.comment = comment;
		this.menu = menu;
	}
 
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}
	
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getComment() {
		return comment;
	}
	
	public Date getDate() {
		return date;
	}
	
	public MenuModel getMenu() {
		return menu;
	}
	
	public Double getRating() {
		return rating;
	}
	
	public UserModel getUser() {
		return user;
	}
	
}