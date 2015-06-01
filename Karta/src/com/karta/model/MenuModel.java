package com.karta.model;
 
public class MenuModel {
	private Integer id;
	private String name;
	private String description;
	private String currency;
	private double price;
	private double rating;
	private Boolean halal;	
	private String[] ingredients;
	private String thumb_image;
	private String[] images;
	private RestaurantModel restaurant;
	private CategoryModel[] category;
	
	public MenuModel(){}
 
	public MenuModel(String name, String description, String currency, double price, double rating, Boolean halal, String[] ingridients, CategoryModel[] category, RestaurantModel restaurant, String thumb_image, String[] images) {
		this.name = name;
		this.description = description;
		this.currency = currency;
		this.price = price;
		this.ingredients = ingridients;
		this.rating = rating;
		this.halal = halal;
		this.thumb_image = thumb_image;
		this.images = images;
		this.category = category;
		this.restaurant = restaurant;
	}
 
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public void setHalal(Boolean halal) {
		this.halal = halal;
	}
	
	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}
	
	public void setThumb_image(String thumb_image) {
		this.thumb_image = thumb_image;
	}
	
	public void setImages(String[] images) {
		this.images = images;
	}
	
	public void setCategory(CategoryModel[] category) {
		this.category = category;
	}
	
	public void setRestaurant(RestaurantModel restaurant) {
		this.restaurant = restaurant;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public double getPrice() {
		return price;
	}
	
	public Boolean getHalal() {
		return halal;
	}
	
	public String[] getIngredients() {
		return ingredients;
	}
	
	public double getRating() {
		return rating;
	}
	
	public String getThumb_image() {
		return thumb_image;
	}
	
	public String[] getImages() {
		return images;
	}
	
	public CategoryModel[] getCategory() {
		return category;
	}
	
	public RestaurantModel getRestaurant() {
		return restaurant;
	}
	
}