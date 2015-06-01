package com.karta.model;
 
public class RestaurantModel {
	private Integer id;
	private String name;
	private String description;
	private String address;
	private String phone;
	private String website;
	private String email;
	private String zipCode;
	private String[] operationHours;
	private String[] profileImage;
	private double rating;
	private double longitude;
	private double latitude;
	private CityModel city;
	private MenuModel[] menu;
	private MenuModel[] menuRecomended;
 
	public RestaurantModel() {}
	
	public RestaurantModel(Integer id, String name, String description, String address, String phone, String website, String email, String zipCode, String[] operationHours, String[] profileImage, double rating, double latitude, double longitude, CityModel city, MenuModel[] menu, MenuModel[] menuRecomended) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.website = website;
		this.email = email;
		this.zipCode = zipCode;
		this.operationHours = operationHours;
		this.profileImage = profileImage;
		this.rating = rating;
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.menu = menu;
		this.menuRecomended = menuRecomended;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
 
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public void setOperationHours(String[] operationHours) {
		this.operationHours = operationHours;
	}

	public void setProfileImage(String[] profileImage) {
		this.profileImage = profileImage;
	}
	
	public void setCity(CityModel city) {
		this.city = city;
	}
	
	public void setMenu(MenuModel[] menu) {
		this.menu = menu;
	}
	
	public void setMenuRecomended(MenuModel[] menuRecomended) {
		this.menuRecomended = menuRecomended;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public double getRating() {
		return rating;
	}
	
	public String[] getOperationHours() {
		return operationHours;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String[] getProfileImage() {
		return profileImage;
	}
	
	public CityModel getCity() {
		return city;
	}
	
	public MenuModel[] getMenu() {
		return menu;
	}
	
	public MenuModel[] getMenuRecomended() {
		return menuRecomended;
	}
	
	public String getDistance(){
		return "2.1 Miles";
	}
	
}