package com.example.demo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

@JsonIgnoreType
public class DishId implements Serializable{
    private static final long serialVersionUID = 1L;
	private String restaurant_name;
	private String name;
	public DishId() {}
	public DishId(String restaurant_name, String name) {
		this.restaurant_name = restaurant_name;
		this.name = name;
	}
	
	public String getRestaurantName() {
		return restaurant_name;
	}
	public void setRestaurantName(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
