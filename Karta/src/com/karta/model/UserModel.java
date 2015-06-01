package com.karta.model;
 
public class UserModel {
	private Integer id;
	private String name;
	private String image;
 
	public UserModel(String name, String image) {
		this.name = name;
		this.image = image;
	}
 
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImage() {
		return image;
	}
	
}