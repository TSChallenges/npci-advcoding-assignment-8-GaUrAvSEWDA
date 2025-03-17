package com.mystore.app.rest;

import com.mystore.app.entity.Product;
import com.mystore.app.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // CREATE a new product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) {
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // GET all products (supports pagination & sorting)
    @GetMapping
    public Page<Product> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return productService.getAllProducts(page, pageSize, sortBy, sortDir);
    }

    // GET product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        Product p = productService.getProduct(id);
        if (p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    // UPDATE product details
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id,
                                                 @Valid @RequestBody Product product) {
        Product updated = productService.updateProduct(id, product);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // DELETE a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Return 204 No Content on successful delete
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // SEARCH products by name
    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }

    // FILTER products by category
    @GetMapping("/filter/category")
    public List<Product> filterByCategory(@RequestParam String category) {
        return productService.filterByCategory(category);
    }

    // FILTER products by price range
    @GetMapping("/filter/price")
    public List<Product> filterByPriceRange(@RequestParam Double minPrice,
                                            @RequestParam Double maxPrice) {
        return productService.filterByPriceRange(minPrice, maxPrice);
    }

    // FILTER products by stock quantity range
    @GetMapping("/filter/stock")
    public List<Product> filterByStockRange(@RequestParam Integer minStock,
                                            @RequestParam Integer maxStock) {
        return productService.filterByStockRange(minStock, maxStock);
    }
}
