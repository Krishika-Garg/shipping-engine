package com.jumbotail.shipping.service;

import com.jumbotail.shipping.model.Seller;
import com.jumbotail.shipping.model.Warehouse;
import com.jumbotail.shipping.repository.SellerRepository;
import com.jumbotail.shipping.repository.WarehouseRepository;
import com.jumbotail.shipping.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final SellerRepository sellerRepository;
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(SellerRepository sellerRepository,
                            WarehouseRepository warehouseRepository) {
        this.sellerRepository = sellerRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse findNearestWarehouse(Long sellerId) {

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        if (seller.getLatitude() == null || seller.getLongitude() == null) {
            throw new RuntimeException("Seller location not available");
        }

        List<Warehouse> warehouses = warehouseRepository.findAll();

        Warehouse nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouses) {

            if (warehouse.getLatitude() == null || warehouse.getLongitude() == null) {
                continue; // skip invalid warehouse
            }

            double distance = calculateDistance(
                    seller.getLatitude(), seller.getLongitude(),
                    warehouse.getLatitude(), warehouse.getLongitude()
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearest = warehouse;
            }
        }

        if (nearest == null) {
            throw new RuntimeException("No valid warehouses found");
        }

        if (warehouses.isEmpty()) {
            throw new ResourceNotFoundException("No warehouses available");
        }
        return nearest;
    }
    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in KM

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // distance in KM
    }
}