package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Dish;
import com.example.demo.model.Restaurant;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestaurantController {
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private DishRepository dishRepository;

	@PostMapping("/restaurant")
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant r) {
		if (r.getName() == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		if (restaurantRepository.existsById(r.getName())) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		try {
			r = restaurantRepository.saveAndFlush(r);
		}
		catch (Exception e){
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(r, HttpStatus.OK);
	}
	
	@GetMapping("/restaurant")
	public ResponseEntity<List<Restaurant>> getAllRestaurant() {
		List<Restaurant> r = restaurantRepository.findAll();
		r.forEach(e -> {
			e.setDish(dishRepository.getDishByRestaurantName(e.getName()));
		});
		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@GetMapping("/restaurant/{name}")
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable("name") String name) {
		Restaurant r = restaurantRepository.findById(name).orElse(null);
		if (r != null) {
			r.setDish(dishRepository.getDishByRestaurantName(r.getName()));
		}
		return new ResponseEntity<>(r, HttpStatus.OK);
	}
	
	@PutMapping("/restaurant/{name}")
	public ResponseEntity<Restaurant> modifyRestaurant(@RequestBody Restaurant r, @PathVariable("name") String name) {
		Restaurant ori_r = restaurantRepository.findById(name).orElse(null);
		if (!Objects.nonNull(ori_r)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		if (Objects.nonNull(r.getCity()) && !"".equalsIgnoreCase(r.getCity())) {
			ori_r.setCity(r.getCity());
		}
		if (Objects.nonNull(r.getRating())) {
			ori_r.setRating(r.getRating());
		}

		try {
			r = restaurantRepository.saveAndFlush(ori_r);
		}
		catch (Exception e){
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(r, HttpStatus.OK);
	}
	
	@DeleteMapping("/restaurant")
	public ResponseEntity<String> deleteAllRestaurant() {
		restaurantRepository.deleteAll();
		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}
	
	@DeleteMapping("/restaurant/{name}")
	public ResponseEntity<String> deleteRestaurant(@PathVariable("name") String name) {
		try {
			restaurantRepository.deleteById(name);
		}
		catch (Exception e) {
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}
	
	@GetMapping("restaurant/search")
	public ResponseEntity<List<Restaurant>> searchRestaurant(
			@RequestParam(value = "city", required = false, defaultValue = "") String city,
			@RequestParam(value = "rating", defaultValue = "0") Integer rating,
			@RequestParam(value = "dish_rating", defaultValue = "0") Integer dish_rating,
			@RequestParam(value = "count", defaultValue = "0") Integer count) {
		List<Restaurant> res_list = null;
		if(!city.equals("")) {
			res_list = restaurantRepository.getRestaurantByCity(city);
		}
		else {
			res_list = restaurantRepository.findAll();
			System.out.println("all");
		}
		res_list.forEach(e -> {
			e.setDish(dishRepository.getDishByRestaurantName(e.getName()));
		});
		System.out.println(res_list.size());
		
		if(rating != 0) {
			res_list = res_list.stream()
					.filter(r -> r.getRating() >= rating)
					.collect(Collectors.toList());
		}
		if(dish_rating != 0) {
			res_list.forEach(r -> {
				r.setDish(r.getDish().stream()
						.filter(d -> d.getRating() >= dish_rating)
						.collect(Collectors.toList()));
			});
		}
		
		res_list.forEach(r -> {
			List<Dish> d = r.getDish();
			Collections.sort(d, (d1, d2) ->{
				return d2.getRating() - d1.getRating();
			});
		});
		
		Collections.sort(res_list, (r1, r2) -> {
			if (r1.getRating() < r2.getRating()) return 1;
			else if (r1.getRating() == r2.getRating()) return 0;
			return -1;
		});
		
		if (count != 0) {
			res_list = res_list.subList(0, count);
		}

		return new ResponseEntity<>(res_list, HttpStatus.OK);
	}
}
