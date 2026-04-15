package com.jumbotail.shipping.service;

import com.jumbotail.shipping.dto.ShippingResponseDTO;
import com.jumbotail.shipping.model.Customer;
import com.jumbotail.shipping.model.Product;
import com.jumbotail.shipping.model.Warehouse;
import com.jumbotail.shipping.repository.CustomerRepository;
import com.jumbotail.shipping.repository.ProductRepository;
import com.jumbotail.shipping.repository.WarehouseRepository;
import com.jumbotail.shipping.exception.ResourceNotFoundException;
import com.jumbotail.shipping.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final WarehouseService warehouseService;

    public ShippingService(WarehouseRepository warehouseRepository,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository,
                           WarehouseService warehouseService) {
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.warehouseService = warehouseService;
    }

    // ✅ Requirement #2
    public double calculateShipping(Long warehouseId,
                                    Long customerId,
                                    Long productId,
                                    String deliverySpeed) {
        if (!deliverySpeed.equalsIgnoreCase("standard") &&
                !deliverySpeed.equalsIgnoreCase("express")) {
            throw new BadRequestException("Invalid delivery speed. Use 'standard' or 'express'");
        }

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double distance = calculateDistance(
                warehouse.getLatitude(), warehouse.getLongitude(),
                customer.getLatitude(), customer.getLongitude()
        );

        double rate;

        if (distance <= 100) {
            rate = 3;
        } else if (distance <= 500) {
            rate = 2;
        } else {
            rate = 1;
        }

        double shippingCost = distance * rate * product.getWeight();

        double finalCost = 10 + shippingCost;

        if ("express".equalsIgnoreCase(deliverySpeed)) {
            finalCost += 1.2 * product.getWeight();
        }

        return finalCost;
    }

    // Requirement #3 (Combined API)
    public ShippingResponseDTO calculateFullShipping(
            Long sellerId,
            Long customerId,
            Long productId,
            String deliverySpeed) {

        // 1️⃣ Find nearest warehouse
        Warehouse nearest = warehouseService.findNearestWarehouse(sellerId);

        // 2️⃣ Calculate shipping
        double charge = calculateShipping(
                nearest.getId(),
                customerId,
                productId,
                deliverySpeed
        );

        // 3️⃣ Return response DTO
        return new ShippingResponseDTO(
                charge,
                nearest.getId(),
                nearest.getLatitude(),
                nearest.getLongitude()
        );
    }

    // Distance calculation (Haversine)
    private double calculateDistance(double lat1, double lon1,
                                     double lat2, double lon2) {

        final int R = 6371; // Earth radius in KM

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}