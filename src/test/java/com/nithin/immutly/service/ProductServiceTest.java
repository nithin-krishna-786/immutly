package com.nithin.immutly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.nithin.immutly.dto.ProductDTO;
import com.nithin.immutly.entity.Product;
import com.nithin.immutly.exceptions.ProductNotFoundException;
import com.nithin.immutly.mapper.Mapper;
import com.nithin.immutly.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@Test
	void testFindAll() {
		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("iPhone X");
		product1.setDescription("Apple iPhone");
		product1.setPrice(50000D);
		product1.setAvailabilityStatus(true);

		Product product2 = new Product();
		product1.setId(2L);
		product1.setName("Moto G71");
		product1.setDescription("Motorolla G71");
		product1.setPrice(17000D);
		product1.setAvailabilityStatus(true);

		List<Product> mockProducts = Arrays.asList(product1, product2);

		when(productRepository.findAll(Sort.by("id"))).thenReturn(mockProducts);

		List<ProductDTO> result = productService.findAllProducts();

		List<ProductDTO> expected = Arrays.asList(Mapper.mapToProductDTO(product1), Mapper.mapToProductDTO(product2));

		assertEquals(expected, result);
	}

	@Test
	void testGetProductById() {

		Long productId = 1L;
		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("iPhone X");
		product1.setDescription("Apple iPhone");
		product1.setPrice(50000D);
		product1.setAvailabilityStatus(true);

		Optional<Product> mockProductOptional = Optional.of(product1);
		when(productRepository.findById(productId)).thenReturn(mockProductOptional);

		ProductDTO result = productService.getProductById(productId);
		ProductDTO expected = Mapper.mapToProductDTO(product1);

		assertEquals(expected, result);
	}

	@Test
	void testGetProductByIdNotFound() {
		Long productId = 1L;
		Optional<Product> mockProductOptional = Optional.empty();
		when(productRepository.findById(productId)).thenReturn(mockProductOptional);
		assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
	}
	
    @Test
    void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        
        productDTO.setId(1L);
        productDTO.setName("iPhone X");
        productDTO.setDescription("Apple iPhone");
		productDTO.setPrice(50000D);
		productDTO.setAvailabilityStatus(true);
		
        Product mappedProduct = Mapper.mapToProduct(productDTO);
        
        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("iPhone X");
        savedProduct.setDescription("Apple iPhone");
        savedProduct.setPrice(50000D);
        savedProduct.setAvailabilityStatus(true);

        when(productRepository.save(mappedProduct)).thenReturn(savedProduct);

        ProductDTO result = productService.createProduct(productDTO);
        ProductDTO expected = Mapper.mapToProductDTO(savedProduct);

        assertEquals(expected, result);
    }
    
    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        
        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setId(productId);
        updatedProductDTO.setName("iPhone X");
        updatedProductDTO.setDescription("Apple iPhone");
        updatedProductDTO.setPrice(50000D);
        updatedProductDTO.setAvailabilityStatus(true);
        
        Product updatedProduct = Mapper.mapToProduct(updatedProductDTO);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        ProductDTO result = productService.updateProduct(productId, updatedProductDTO);
        ProductDTO expected = Mapper.mapToProductDTO(updatedProduct);

        assertEquals(expected, result);
    }

    @Test
    void testUpdateProductNotFound() {
        Long productId = 1L;
        ProductDTO updatedProductDTO = new ProductDTO();
        when(productRepository.existsById(productId)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, updatedProductDTO));
    }
    
    @Test
    void testDeleteProductById() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProductById(productId);

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductByIdNotFound() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(productId));

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).deleteById(any());
    }
	
    @Test
    void testGetProductsByName() {
        // Mock data
        String productName = "Moto";
        
		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("Moto G51");
		product1.setDescription("Motorolla G51");
		product1.setPrice(15000D);
		product1.setAvailabilityStatus(true);

		Product product2 = new Product();
		product1.setId(2L);
		product1.setName("Moto G71");
		product1.setDescription("Motorolla G71");
		product1.setPrice(17000D);
		product1.setAvailabilityStatus(true);
        
        
        List<Product> mockProducts = Arrays.asList(product1, product2);

        when(productRepository.findAllByNameContainingIgnoreCase(productName)).thenReturn(mockProducts);

        List<ProductDTO> result = productService.getProductsByName(productName);

        verify(productRepository, times(1)).findAllByNameContainingIgnoreCase(productName);

        List<ProductDTO> expected = Arrays.asList(
                Mapper.mapToProductDTO(product1),
                Mapper.mapToProductDTO(product2)
        );

        assertEquals(expected, result);
    }

    @Test
    void testGetProductsByPriceRange() {
        // Mock data
        Double minPrice = 15000D;
        Double maxPrice = 20000D;
        
		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("Moto G51");
		product1.setDescription("Motorolla G51");
		product1.setPrice(15000D);
		product1.setAvailabilityStatus(true);

		Product product2 = new Product();
		product1.setId(2L);
		product1.setName("Moto G71");
		product1.setDescription("Motorolla G71");
		product1.setPrice(17000D);
		product1.setAvailabilityStatus(true);
        List<Product> mockProducts = Arrays.asList(product1, product2);

        when(productRepository.findAllByPriceBetween(minPrice, maxPrice)).thenReturn(mockProducts);
        List<ProductDTO> result = productService.getProductsByPriceRange(minPrice, maxPrice);
        verify(productRepository, times(1)).findAllByPriceBetween(minPrice, maxPrice);

        List<ProductDTO> expected = Arrays.asList(
                Mapper.mapToProductDTO(product1),
                Mapper.mapToProductDTO(product2)
        );

        assertEquals(expected, result);
    }
	
}
