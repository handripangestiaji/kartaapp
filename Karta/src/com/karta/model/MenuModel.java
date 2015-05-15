package com.karta.model;
 
public class MenuModel {
	private String name;
	private String description;
	private double price;
	private double rating;
	private double longitude;
	private double latitude;
	private String category;
	private String restoranName;
	private String[] images;
 
	public MenuModel(String name, String description, double price, double rating, double latitude, double longitude, String category, String restoranName, String[] images) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.rating = rating;
		this.latitude = latitude;
		this.longitude = longitude;
		this.category = category;
		this.restoranName = restoranName; 
		this.images = images; 
	}
 
	public String getName() {
		return this.name;
	}
	 
	public String getDescription() {
		return this.description;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public double getRating() {
		return this.rating;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
		
	public double getLongitude() {
		return this.longitude;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getRestoranName() {
		return restoranName;
	}

	public String[] getImages() {
		return images;
	}
}