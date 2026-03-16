package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Component;

// MODIFICARE: Am schimbat <Component, Long> in <Component, Integer>
public interface ComponentRepository extends JpaRepository<Component, Integer> {
	Component findByName(String name);
	Component findByCode(String name);
    // Metodele standard (save, findAll, findById, delete) sunt incluse automat
}