package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Product;

// MODIFICARE: Am schimbat <Product, Long> in <Product, Integer>
public interface ProductRepository extends JpaRepository<Product, Integer> {
	Product findByName(String name);
	// Aici poti adauga metode de cautare daca ai nevoie in viitor
	// Ex: List<Product> findByCategory(String category);
}