package com.example.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ProductDto;
import com.example.entities.Product;
import com.example.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ModelMapper mapper = new ModelMapper();

	@Autowired
	private ProductService productService;

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@PostMapping("/createProduct")
	public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
		Product product = mapper.map(productDto, Product.class);
		Product createdProduct = productService.createProduct(product);
		return ResponseEntity.ok(createdProduct);

	}

	@PutMapping("updateProduct/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
		Product product = mapper.map(productDto, Product.class);
		return productService.updateProduct(id, product);
	}

	@DeleteMapping("deleteProduct/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}
}
