package com.nithin.immutly.mapper;

import com.nithin.immutly.dto.ProductDTO;
import com.nithin.immutly.entity.Product;

public class Mapper {

	public static ProductDTO mapToProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		productDTO.setAvailabilityStatus(product.getAvailabilityStatus());
		return productDTO;
	}

	public static Product mapToProduct(ProductDTO productDTO) {
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setAvailabilityStatus(productDTO.getAvailabilityStatus());
		return product;
	}

}
