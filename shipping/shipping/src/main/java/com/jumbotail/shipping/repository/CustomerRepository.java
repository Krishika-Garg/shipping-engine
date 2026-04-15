package com.jumbotail.shipping.repository;

import com.jumbotail.shipping.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}