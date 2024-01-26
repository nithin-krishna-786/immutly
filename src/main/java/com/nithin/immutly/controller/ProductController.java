package com.nithin.immutly.controller;

import com.nithin.immutly.dto.ProductDTO;
import com.nithin.immutly.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice, @RequestParam(required = false) String productName) {
		List<ProductDTO> result;
		if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
			throw new IllegalArgumentException("minPrice should be less than or equal to maxPrice");
		}

		if (minPrice != null && maxPrice != null) {
			result = productService.getProductsByPriceRange(minPrice, maxPrice);
		} else if (productName != null) {
			result = productService.getProductsByName(productName);
		} else {
			result = productService.findAllProducts();
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{product_id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable(name = "product_id") Long id) {
		ProductDTO result = productService.getProductById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
		productDTO = productService.createProduct(productDTO);
		return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{product_id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable(name = "product_id") Long id,
			@RequestBody @Valid ProductDTO productDTO) {
		productDTO = productService.updateProduct(id, productDTO);
		return ResponseEntity.ok(productDTO);
	}

	@DeleteMapping("/{product_id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(name = "product_id") Long id) {
		productService.deleteProductById(id);
		return ResponseEntity.noContent().build();
	}

}
