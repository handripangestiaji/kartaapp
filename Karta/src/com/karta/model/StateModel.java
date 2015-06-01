package com.karta.model;
 
public class StateModel {
	private Integer id;
	private String name;
 
	public StateModel() { }

	public StateModel(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
 
	public Integer getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}
	 	
}