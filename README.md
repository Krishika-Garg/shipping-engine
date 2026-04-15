# Shipping Cost Engine (Backend System)

## Overview
This project implements a backend service to calculate shipping charges between sellers, warehouses, and customers based on geographical distance, product attributes, and delivery speed.

The system is designed using a layered architecture with clear separation of concerns, ensuring scalability, maintainability, and ease of testing. It focuses on correctness, validation, and structured error handling, making it suitable for production-grade backend systems.

---

## Tech Stack
- Java 17  
- Spring Boot  
- Spring Data JPA  
- H2 Database  
- RESTful APIs  
- Maven  
- Postman (API Testing)

---

## System Architecture

The application follows a layered architecture pattern:

### Controller Layer
- Handles HTTP requests/responses  
- Maps API calls to services  
- Keeps logic minimal  

### Service Layer
- Core business logic  
- Shipping cost calculation  
- Distance computation using Haversine formula  
- Nearest warehouse selection  

### Repository Layer
- Database interaction using Spring Data JPA  
- Abstracts persistence logic  

### Entity Layer
- Represents database tables  
- Uses JPA annotations  

### DTO Layer
- Separates internal models from API responses  
- Improves flexibility and security  

### Exception Handling
- Centralized using @RestControllerAdvice  
- Returns structured error responses  

---

## Key Features

- Distance-based shipping calculation using Haversine formula  
- Nearest warehouse selection for optimized delivery  
- Dynamic pricing based on distance, weight, and delivery speed  
- Robust validation & error handling  
- API testing across multiple scenarios using Postman  

---

## Shipping Logic

ShippingCost = Distance × Rate × ProductWeight  
FinalCost = 10 + ShippingCost  

### Rate Slabs:
- ≤ 100 KM → Rate = 3  
- ≤ 500 KM → Rate = 2  
- > 500 KM → Rate = 1  

### Additional:
- Express delivery adds extra charges  

---

## 📡 API Endpoints

### 1. Get Nearest Warehouse
```
GET /api/v1/warehouse/nearest?sellerId={id}
```

---

### 2. Calculate Shipping (Manual)
```
GET /api/v1/shipping-charge?warehouseId=&customerId=&productId=&deliverySpeed=
```

---

### 3. Calculate Shipping (Auto Warehouse)
```
POST /api/v1/shipping-charge/calculate
```

#### Request:
```json
{
  "sellerId": 1,
  "customerId": 1,
  "productId": 1,
  "deliverySpeed": "standard"
}
```

#### Response:
```json
{
  "shippingCharge": 2967.47,
  "warehouseId": 1,
  "latitude": 12.99999,
  "longitude": 77.923273
}
```

---

## Testing

* Tested APIs using Postman
* Covered 10+ scenarios, including:
   * Valid requests
   * Invalid IDs
   * Edge cases (no warehouse, invalid delivery type)
* Ensured correctness and reliability of outputs

---

## Exception Handling

Handles cases like:

* Seller/Warehouse/Customer/Product not found
* Invalid delivery speed
* Missing parameters

### Example Error Response:

```json
{
  "timestamp": "...",
  "status": 404,
  "error": "Not Found",
  "message": "Seller not found"
}
```

---

## Data Model

### Entities:

* Seller
* Warehouse
* Customer
* Product

### Includes:

* Location coordinates (latitude, longitude)
* Product attributes (weight, dimensions, price)

---

## How to Run

1. Clone the repository
2. Run using Maven
3. Access app at: http://localhost:8080
4. H2 Console: http://localhost:8080/h2-console

---

## Future Enhancements

* Swagger/OpenAPI documentation
* MySQL/PostgreSQL integration
* Caching for warehouse lookup
* Docker containerization
* Configurable pricing strategies

---

## Key Learnings

* Designing layered backend systems
* Handling real-world validation and edge cases
* Writing clean, maintainable code
* Building and testing reliable APIs
