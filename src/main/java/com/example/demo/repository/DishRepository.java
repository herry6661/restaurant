package com.example.demo.repository;

import com.example.demo.model.Dish;
import com.example.demo.model.DishId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, DishId>{
	// @Query("SELECT r FROM restaurant r")
	// List<Restaurant> getAllRestaurant();
	@Query(nativeQuery = true, value = "SELECT * FROM Dish WHERE restaurant_name = ?")
	public List<Dish> getDishByRestaurantName(@Param("restaurant_name") String restaurant_name);
}
