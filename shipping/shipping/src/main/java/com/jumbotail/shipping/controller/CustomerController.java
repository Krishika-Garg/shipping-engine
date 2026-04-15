package com.jumbotail.shipping.controller;

import com.jumbotail.shipping.model.Customer;
import com.jumbotail.shipping.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.jumbotail.shipping.dto.CustomerRequestDTO;
import com.jumbotail.shipping.dto.CustomerResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public CustomerResponseDTO createCustomer(
            @Valid @RequestBody CustomerRequestDTO request) {

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setLatitude(request.getLatitude());
        customer.setLongitude(request.getLongitude());

        Customer saved = customerRepository.save(customer);

        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setPhone(saved.getPhone());
        response.setLatitude(saved.getLatitude());
        response.setLongitude(saved.getLongitude());

        return response;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}