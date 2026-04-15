package com.jumbotail.shipping.controller;

import com.jumbotail.shipping.model.Warehouse;
import com.jumbotail.shipping.repository.WarehouseRepository;
import com.jumbotail.shipping.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseRepository warehouseRepository,
                               WarehouseService warehouseService) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseService = warehouseService;
    }

    // POST /warehouses
    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    // GET /warehouses
    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    // MAIN ASSIGNMENT API
    @GetMapping("/nearest")
    public Warehouse getNearestWarehouse(@RequestParam Long sellerId) {
        return warehouseService.findNearestWarehouse(sellerId);
    }
}