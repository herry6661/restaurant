package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.IdClass;
import com.example.demo.model.DishId;

import java.io.Serializable;

@Entity
@Table (name = "dish")
@IdClass(DishId.class)
public class Dish implements Serializable{
    private static final long serialVersionUID = 1L;

	public Dish() {}

	@Id
	@Column(name = "restaurant_name")
	private String restaurant_name;

	@Id
	@Column(name = "dish_name")
	private String name;

	@Column(name = "dish_price")
	private Integer price;

	@Column(name = "dish_rating")
	private Integer rating;
	
	public DishId getId() {
		return new DishId(restaurant_name, name);
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}