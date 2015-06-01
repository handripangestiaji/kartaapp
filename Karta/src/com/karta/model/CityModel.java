package com.karta.model;
 
public class CityModel {
	private Integer id;
	private String name;
	StateModel state;
 
	public CityModel() { }
	
	public CityModel(Integer id, String name, StateModel state) {
		this.id = id;
		this.name = name;
		this.state = state;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setState(StateModel state) {
		this.state = state;
	}
	
	public Integer getId() {
		return id;
	}
 
	public String getName() {
		return this.name;
	}
	
	public StateModel getState() {
		return state;
	}
	 
}