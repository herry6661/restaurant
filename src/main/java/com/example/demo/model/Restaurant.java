package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import com.example.demo.model.Dish;

@Entity
@Table (name = "restaurant")
public class Restaurant {
	
	public Restaurant() {}
	
	public Restaurant(String name, String city) {
		this(name, city, 0f);
	}
	
	public Restaurant(String name, String city, Float rating) {
		this.name = name;
		this.city = city;
		this.rating = rating;
	}

	@Id
	@Column(name = "restaurant_name")
	private String name;

	@Column(name = "restaurant_city")
	private String city;

	@Column(name = "restaurant_rating")
	private Float rating = 0f;
	
	@Transient
	private List<Dish> dish = null;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}
	
	public List<Dish> getDish() {
		return dish;
	}
	
	public void setDish(List<Dish> dish) {
		this.dish = dish;
	}

}