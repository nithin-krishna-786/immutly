package com.nithin.immutly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nithin.immutly.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByNameContainingIgnoreCase(String name);
	List<Product> findAllByPriceBetween(Double minPrice,Double MaxPrice);
}
