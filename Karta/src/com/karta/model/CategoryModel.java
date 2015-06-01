package com.karta.model;
 
public class CategoryModel {
	private Integer id;
	private String name;
	private String image;
 
	public CategoryModel() { }

	public CategoryModel(Integer id, String name, String image) {
		this.id = id;
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
		return this.name;
	}
	 
	public String getImage() {
		return this.image;
	}	
	
}