package com.jumbotail.shipping.controller;

import com.jumbotail.shipping.service.ShippingService;
import com.jumbotail.shipping.dto.ShippingRequestDTO;
import com.jumbotail.shipping.dto.ShippingResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping("/shipping-charge")
    public double getShippingCharge(
            @RequestParam Long warehouseId,
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam String deliverySpeed) {

        return shippingService.calculateShipping(
                warehouseId,
                customerId,
                productId,
                deliverySpeed
        );
    }

    @PostMapping("/shipping-charge/calculate")
    public ShippingResponseDTO calculateFullShipping(
            @RequestBody ShippingRequestDTO request) {

        return shippingService.calculateFullShipping(
                request.getSellerId(),
                request.getCustomerId(),
                request.getProductId(),
                request.getDeliverySpeed()
        );
    }
}