package com.ecommerce.esmsproject1.repository;

import com.ecommerce.esmsproject1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Long> {
}
