# Base REST API

A well-structured Spring Boot REST API that serves as a foundation for microservices development. This project implements an Order Management System with best practices and modern features.

## Features

- Spring Boot 3.2.3 with Java 21
- RESTful API with OpenAPI documentation
- PostgreSQL database (with H2 for testing)
- JPA/Hibernate for data persistence
- Bean validation
- Exception handling with global error responses
- Records for DTOs (immutable data classes)
- Lombok for reducing boilerplate
- Comprehensive unit tests
- Custom banner
- Profile-based configuration (local, docker, test)

## Project Structure

```
src/main/java/com/example/baserestapi/
├── BaseRestApiApplication.java
├── controller/
│   └── OrderController.java
├── domain/
│   ├── Order.java
│   ├── OrderItem.java
│   └── OrderStatus.java
├── dto/
│   ├── OrderRequest.java
│   ├── OrderResponse.java
│   ├── OrderItemRequest.java
│   └── OrderItemResponse.java
├── repository/
│   └── OrderRepository.java
├── service/
│   ├── OrderService.java
│   └── OrderServiceImpl.java
└── exception/
    └── GlobalExceptionHandler.java
```

## Prerequisites

- Java 21
- Maven
- Docker and Docker Compose (for PostgreSQL)
- PostgreSQL (for local development)

## Database Configuration

The application supports three different profiles:

### Local Development (default)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/orderdb
    username: postgres
    password: postgres
```

### Docker Environment
```yaml
spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/orderdb
    username: postgres
    password: postgres
```

### Testing Environment
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: password
```

## Getting Started

### Running Locally with Docker PostgreSQL

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd base-rest-api
   ```

2. Start PostgreSQL using Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Build the application:
   ```bash
   ./mvnw clean package
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The application will be available at http://localhost:8080

### Running Locally with Native PostgreSQL

1. Create PostgreSQL database:
   ```bash
   createdb orderdb
   ```

2. Build and run the application:
   ```bash
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```

## API Documentation

Once the application is running, you can access:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI docs: http://localhost:8080/api-docs

## API Endpoints and Examples

### Orders API

#### Create Order
```http
POST /api/v1/orders
Content-Type: application/json

{
  "customerName": "John Doe",
  "items": [
    {
      "productName": "Product 1",
      "quantity": 2,
      "price": 10.00
    }
  ]
}
```

#### Get Order
```http
GET /api/v1/orders/{id}
```

#### Update Order Status
```http
PATCH /api/v1/orders/{id}/status?status=CONFIRMED
```

Available statuses:
- PENDING
- CONFIRMED
- SHIPPED
- DELIVERED
- CANCELLED

## Postman Collection

A Postman collection is included in the root directory (`Base-REST-API.postman_collection.json`). To use it:

1. Import the collection into Postman
2. Set up an environment with the variable `baseUrl` (default: http://localhost:8080)
3. Use the collection to test all available endpoints

The collection includes:
- Order management endpoints
- Environment variables for easy configuration

## Error Handling

The API uses a global exception handler to provide consistent error responses:

```json
{
  "status": 404,
  "message": "Order not found with id: 1",
  "errors": null,
  "timestamp": "2024-03-12T10:30:00"
}
```

Validation errors:
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "customerName": "Customer name is required",
    "items": "Order must contain at least one item"
  },
  "timestamp": "2024-03-12T10:30:00"
}
```

## Testing

The project includes comprehensive unit tests for both service and controller layers:

### Service Tests
- Order creation
- Order retrieval
- Status updates
- Error scenarios

### Controller Tests
- API endpoint testing
- Request validation
- Error responses

Run tests with:
```bash
./mvnw test
```

## Future Enhancements

This API is designed to be extended with:
- CQRS pattern
- Event-Driven Architecture
- Microservices using Spring Cloud
- Message queues (e.g., Kafka)
- Service discovery
- API Gateway
- Circuit breakers
- Monitoring with Prometheus and Grafana

## Docker Support

The project includes:
- Docker Compose configuration for PostgreSQL database
- Volume persistence for database data
- Health checks for database service

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 