package com.example.demo.repository;

import com.example.demo.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, String>{
	// @Query(nativeQuery = true, value = "SELECT r FROM Restaurant r WHERE r.restaurant_city = %?1%")
	@Query(nativeQuery = true, value = "SELECT * FROM Restaurant WHERE restaurant_city = ?")
	public List<Restaurant> getRestaurantByCity(@Param("restaurant_city") String city);
}
