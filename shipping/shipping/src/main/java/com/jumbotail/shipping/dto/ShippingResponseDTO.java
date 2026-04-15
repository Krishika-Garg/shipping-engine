package com.jumbotail.shipping.dto;

public class ShippingResponseDTO {

    private double shippingCharge;
    private Long warehouseId;
    private Double latitude;
    private Double longitude;

    public ShippingResponseDTO(double shippingCharge,
                               Long warehouseId,
                               Double latitude,
                               Double longitude) {
        this.shippingCharge = shippingCharge;
        this.warehouseId = warehouseId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}