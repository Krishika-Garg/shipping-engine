package com.jumbotail.shipping.repository;

import com.jumbotail.shipping.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}