package com.karta.model;
 
public class BusinessModel {
	private String name;
	private String city;
	private String address;
	private String[] operationHours;
	private double rating;
	private double longitude;
	private double latitude;
	private String[] images;
 
	public BusinessModel(String name, String city, String address, double rating, double latitude, double longitude, String[] operationHours, String[] images) {
		this.name = name;
		this.city = city;
		this.address = address;
		this.rating = rating;
		this.latitude = latitude;
		this.longitude = longitude;
		this.operationHours = operationHours;
		this.images = this.images;
	}
 
	public String getName() {
		return this.name;
	}
	 
	public String getCity() {
		return this.city;
	}
	
	public String getAddress() {
		return this.address;
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
	
	public String[] getOperationHours() {
		return operationHours;
	}

	public String[] getImages() {
		return images;
	}
	
}