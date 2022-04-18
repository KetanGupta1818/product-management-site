package com.kg.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kg.domain.Product;
import com.kg.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAllProducts(){
		return productRepository.findAll();
	}
	public void updateUnitsInStock(long threshold, long target) {
		productRepository.saveAll(productRepository.findByUnitsInStockLessThan(threshold).stream()
			.map(p->{
				p.setUnitsInStock(target);
				return p;
			}).collect(Collectors.toList()));
		
	}
	public List<Product> getListOfProductsByCategory(String category){
		return productRepository.findByCategory(category);
	}
	public Product getProductByProductid(String productId) {
		return productRepository.findByProductId(productId);
	}
	public void saveProduct(Product product) {
		productRepository.save(product);
	}
}
