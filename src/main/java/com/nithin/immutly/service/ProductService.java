package com.nithin.immutly.service;

import com.nithin.immutly.dto.ProductDTO;
import com.nithin.immutly.entity.Product;
import com.nithin.immutly.exceptions.ProductNotFoundException;
import com.nithin.immutly.mapper.Mapper;
import com.nithin.immutly.repository.ProductRepository;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<ProductDTO> findAllProducts() {
		List<Product> products = productRepository.findAll(Sort.by("id"));
		return products.stream().map(product -> Mapper.mapToProductDTO(product)).collect(Collectors.toList());
	}

	public ProductDTO getProductById(Long id) {
		return productRepository.findById(id).map(product -> Mapper.mapToProductDTO(product))
				.orElseThrow(() -> new ProductNotFoundException("Product not found with given ID:" + id));
	}

	public ProductDTO createProduct(ProductDTO productDTO) {
		Product product = Mapper.mapToProduct(productDTO);
		product = productRepository.save(product);
		return Mapper.mapToProductDTO(product);
	}

	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		if (!productRepository.existsById(id))
			throw new ProductNotFoundException("Product Not Found for given ID:" + id);
		Product product = Mapper.mapToProduct(productDTO);
		product = productRepository.save(product);
		return Mapper.mapToProductDTO(product);
	}
	
	public void deleteProductById(Long id) {
		if (!productRepository.existsById(id))
			throw new ProductNotFoundException("Product Not Found Exception with ID" + id);
		productRepository.deleteById(id);
	}

	public List<ProductDTO> getProductsByName(String name) {
		List<Product> products = productRepository.findAllByNameContainingIgnoreCase(name);
		List<ProductDTO> result = products.stream().map(p -> Mapper.mapToProductDTO(p)).collect(Collectors.toList());
		return result;
	}

	public List<ProductDTO> getProductsByPriceRange(Double minPrice, Double maxPrice) {
		List<Product> products = productRepository.findAllByPriceBetween(minPrice, maxPrice);
		return products.stream().map(p -> Mapper.mapToProductDTO(p)).collect(Collectors.toList());
	}

}
