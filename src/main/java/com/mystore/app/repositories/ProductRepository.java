package com.mystore.app.repositories;

import com.mystore.app.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    // TODO

    // Search by name (case-insensitive substring)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Filter by category (case-insensitive exact match)
    List<Product> findByCategoryIgnoreCase(String category);

    // Filter by price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Filter by stock quantity range
    List<Product> findByStockQuantityBetween(Integer minStock, Integer maxStock);
}
