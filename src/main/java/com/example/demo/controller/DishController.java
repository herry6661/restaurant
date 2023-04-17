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
import com.example.demo.model.Dish;
import com.example.demo.model.DishId;
import com.example.demo.repository.DishRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DishController {
	@Autowired
	private DishRepository dishRepository;

	@PostMapping("/dish")
	public ResponseEntity<Dish> createDish(@RequestBody Dish d) {
		if (d.getName() == null || d.getRestaurantName() == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		if (dishRepository.existsById(d.getId())) {
			return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
		}
		try {
			d = dishRepository.saveAndFlush(d);
		}
		catch (Exception e){
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(d, HttpStatus.OK);
	}
	
	@GetMapping("/dish")
	public ResponseEntity<List<Dish>> getAllDish() {
		return new ResponseEntity<>(dishRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/dish/{restaurant_name}/{name}")
	public ResponseEntity<Dish> getDish(@PathVariable("restaurant_name") String restaurant_name, @PathVariable("name") String name) {
		return new ResponseEntity<>(dishRepository.findById(new DishId(restaurant_name, name)).orElse(null), HttpStatus.OK);
	}
	
	@PutMapping("/dish/{restaurant_name}/{name}")
	public ResponseEntity<Dish> modifyDish(@RequestBody Dish d, @PathVariable("restaurant_name") String restaurant_name, @PathVariable("name") String name) {
		Dish ori_d = dishRepository.findById(new DishId(restaurant_name, name)).orElse(null);
		if (!Objects.nonNull(ori_d)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		if (Objects.nonNull(d.getRestaurantName()) && !"".equalsIgnoreCase(d.getRestaurantName())) {
			ori_d.setRestaurantName(d.getRestaurantName());
		}
		if (Objects.nonNull(d.getPrice())) {
			ori_d.setPrice(d.getPrice());
		}
		if (Objects.nonNull(d.getRating())) {
			ori_d.setRating(d.getRating());
		}

		try {
			d = dishRepository.saveAndFlush(ori_d);
		}
		catch (Exception e){
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(d, HttpStatus.OK);
	}
	
	@DeleteMapping("/dish")
	public ResponseEntity<String> deleteAllDish() {
		dishRepository.deleteAll();
		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}
	
	@DeleteMapping("/dish/{restaurant_name}/{name}")
	public ResponseEntity<String> deleteDish(@PathVariable("restaurant_name") String restaurant_name, @PathVariable("name") String name) {
		try {
			dishRepository.deleteById(new DishId(restaurant_name, name));
		}
		catch (Exception e) {
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}
}
