package com.jumbotail.shipping.repository;

import com.jumbotail.shipping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}