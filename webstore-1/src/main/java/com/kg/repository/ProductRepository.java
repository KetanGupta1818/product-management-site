package com.kg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.kg.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	List<Product> findByUnitsInStockLessThan(long stocks);
	List<Product> findByCategory(String category);
	Product findByProductId(String productId);
}
