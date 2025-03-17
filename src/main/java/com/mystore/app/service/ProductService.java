package com.mystore.app.service;

import com.mystore.app.entity.Product;
import com.mystore.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private Integer currentId = 1;

    @Autowired
    private ProductRepository productRepository;

    // CREATE
    public Product addProduct(Product product) {
        product.setId(currentId++);
        return productRepository.save(product);
    }


    public Page<Product> getAllProducts(int page, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    // GET by ID
    public Product getProduct(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        return optional.orElse(null);
    }

    // UPDATE
    public Product updateProduct(Integer id, Product product) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            return null;  // Will let controller produce 404 if null
        }
        Product existing = optional.get();
        existing.setName(product.getName());
        existing.setCategory(product.getCategory());
        existing.setPrice(product.getPrice());
        existing.setStockQuantity(product.getStockQuantity());
        return productRepository.save(existing);
    }

    // DELETE
    public boolean deleteProduct(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        productRepository.delete(optional.get());
        return true;
    }


    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> filterByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    public List<Product> filterByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> filterByStockRange(Integer minStock, Integer maxStock) {
        return productRepository.findByStockQuantityBetween(minStock, maxStock);
    }
}
