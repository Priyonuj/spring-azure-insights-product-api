# Spring Azure Insights Product API

A Spring Boot REST API for product management with comprehensive Azure Application Insights integration. This application demonstrates best practices for cloud-native monitoring, structured exception handling, and SOLID design principles.

## Features

- **Complete Product Management**: Create, read, update, and delete product information
- **Azure Application Insights Integration**: Comprehensive telemetry tracking for performance monitoring
- **Standardized Exception Handling**: Consistent error responses with automatic telemetry tracking
- **OpenAPI Documentation**: Interactive API documentation with Swagger UI
- **SOLID Design Principles**: Well-structured code following best practices
- **Telemetry Tracking**: Performance metrics, custom events, and exception tracking

## Project Structure

```
com.nexacloud.demoappinsights/
├── configuration/
│   ├── ApplicationInsightsConfig.java   # Configures Azure Application Insights telemetry and initializes the SDK
│   ├── CorsConfig.java                  # Sets up CORS policies for cross-origin requests
│   └── OpenAPIConfig.java               # Configures Swagger/OpenAPI documentation
│
├── controller/
│   └── ProductController.java           # RESTful API endpoints for product CRUD operations
│
├── dto/
│   ├── request/
│   │   ├── CreateProduct.java           # DTO for product creation requests
│   │   └── ProductReq.java              # Generic request DTO for product operations
│   │
│   └── response/
│       ├── ErrorRes.java                # Standardized error response format
│       └── SuccessRes.java              # Standardized success response format
│
├── entity/
│   └── ProductModel.java                # JPA entity for products table
│
├── exception/
│   ├── BaseAppInsightsException.java    # Base exception with Application Insights integration
│   ├── BusinessRuleViolationException.java # Exception for business logic violations
│   ├── GlobalExceptionHandler.java      # Central exception handler for API errors
│   ├── ResourceNotFoundException.java   # Exception for resource not found errors
│   └── ValidationException.java         # Exception for validation errors
│
├── repository/
│   └── ProductRepository.java           # JPA repository for database operations
│
├── service/
│   ├── implementation/
│   │   └── product/
│   │       ├── ProductCreateServiceImpl.java # Implementation for product creation
│   │       ├── ProductDeleteServiceImpl.java # Implementation for product deletion
│   │       ├── ProductFetchServiceImpl.java  # Implementation for product retrieval
│   │       └── ProductUpdateServiceImpl.java # Implementation for product updates
│   │
│   ├── interfaces/
│   │   └── product/
│   │       ├── ProductCreateService.java     # Interface for product creation
│   │       ├── ProductDeleteService.java     # Interface for product deletion
│   │       ├── ProductFetchService.java      # Interface for product retrieval
│   │       └── ProductUpdateService.java     # Interface for product updates
│   │
│   └── ProductService.java              # Facade service combining all product operations
│
├── util/
│   ├── ProductUtil.java                 # Utility methods for product-related operations
│   └── TelemetryUtil.java               # Helper for Application Insights telemetry
│
└── DemoAppInsightsApplication.java      # Main Spring Boot application entry point
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Azure Application Insights resource (for telemetry)

### Configuration

1. Update `application.properties` with your Azure Application Insights connection string:

```properties
azure.application-insights.connection-string=InstrumentationKey=your-key;IngestionEndpoint=https://your-endpoint;LiveEndpoint=https://your-live-endpoint;ApplicationId=your-app-id
```

### Building the Project

```bash
mvn clean install
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on port 8080 by default.

## API Documentation

After starting the application, access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

## API Endpoints

| Method | URL                    | Description                           |
|--------|------------------------|---------------------------------------|
| GET    | /api/products          | Get all products (optional min price) |
| GET    | /api/products/{id}     | Get product by ID                     |
| POST   | /api/products          | Create a new product                  |
| PUT    | /api/products/{id}     | Update an existing product            |
| DELETE | /api/products/{id}     | Delete a product                      |

## Telemetry

The application tracks the following telemetry data:

- **Custom Events**: Product creation, updates, deletions, and queries
- **Metrics**: Processing time, result counts, and product prices
- **Exceptions**: All exceptions with contextual properties
- **Dependencies**: Database and external API calls

## Error Handling

All exceptions follow a standardized format:

```json
{
  "message": "ExceptionType: Detailed error message",
  "error": "Error type",
  "errorCode": "HTTP status code"
}
```

@Author: Priyonuj Dey