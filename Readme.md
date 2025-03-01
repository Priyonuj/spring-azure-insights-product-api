# Spring Boot Application Insights Demo

This project demonstrates how to integrate Azure Application Insights into a Spring Boot application to monitor application performance, track exceptions, and collect custom telemetry data. The application implements a simple product management system with CRUD operations.
## Features

- **Complete Product Management**: Create, read, update, and delete product information
- **Azure Application Insights Integration**: Comprehensive telemetry tracking for performance monitoring
- **Standardized Exception Handling**: Consistent error responses with automatic telemetry tracking
- **OpenAPI Documentation**: Interactive API documentation with Swagger UI
- **SOLID Design Principles**: Well-structured code following best practices
- **Telemetry Tracking**: Performance metrics, custom events, and exception tracking

## Technology Stack

- **Java**: Version 17
- **Spring Boot**: Version 3.4.3
- **Spring Data JPA**: For database operations
- **H2 Database**: In-memory database for development
- **Azure Application Insights**: For monitoring and telemetry
- **Lombok**: To reduce boilerplate code
- **Swagger/OpenAPI**: For API documentation
- **Maven**: For dependency management and build



## Dependencies
The project uses the following key dependencies for Azure Application Insights integration:
```
<!-- Azure Application Insights -->
<dependency>
    <groupId>com.microsoft.azure</groupId>
    <artifactId>applicationinsights-spring-boot-starter</artifactId>
    <version>2.6.4</version>
</dependency>

<dependency>
    <groupId>com.microsoft.azure</groupId>
    <artifactId>applicationinsights-logging-logback</artifactId>
    <version>2.6.4</version>
</dependency>

```

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

### Application Insights Setup
- Create an Azure Application Insights resource
- Get the connection string/instrumentation key
- Configure it in **application.properties**

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

The API is documented using Swagger/OpenAPI and can be accessed at:

Swagger UI:
```
http://localhost:8080/swagger-ui/
```
OpenAPI Docs:
```
http://localhost:8080/v3/api-docs
```
## API Endpoints

| Method | URL                    | Description                           |
|--------|------------------------|---------------------------------------|
| GET    | /api/products          | Get all products (optional min price) |
| GET    | /api/products/{id}     | Get product by ID                     |
| POST   | /api/products          | Create a new product                  |
| PUT    | /api/products/{id}     | Update an existing product            |
| DELETE | /api/products/{id}     | Delete a product                      |

## Application Insights Integration
 
**Configuration**
The Application Insights configuration is done in ApplicationInsightsConfig.java:

```
@Configuration
public class ApplicationInsightsConfig {
@Value("${spring.application.name}")
private String applicationName;

    @Value("${azure.application-insights.connection-string}")
    private String connectionString;

    @Bean
    public TelemetryClient telemetryClient() {
        TelemetryConfiguration configuration = TelemetryConfiguration.createDefault();
        configuration.setConnectionString(connectionString);
        return new TelemetryClient(configuration);
    }

    @Bean
    public TelemetryInitializer telemetryInitializer() {
        return telemetry -> {
            telemetry.getContext().getCloud().setRole(applicationName);
            telemetry.getContext().getProperties().put("environment", "development");
            telemetry.getContext().getComponent().setVersion("1.0.0");
        };
    }
}
```

## Telemetry

The application tracks the following telemetry data:

- **Custom Events**: Product creation, updates, deletions, and queries
- **Metrics**: Processing time, result counts, and product prices
- **Exceptions**: All exceptions with contextual properties
- **Dependencies**: Database and external API calls


## Viewing Telemetry Data
After running the application and generating some traffic:

1. Go to the Azure Portal
2. Navigate to your Application Insights resource
3. Explore the various dashboards and reports:
    - Overview
    - Performance
    - Failures
    - Transaction Search
    - Custom Events
    - Metrics Explorer


## Error Handling
The project implements a global exception handling mechanism that integrates with Application Insights:

- **BaseAppInsightsException**: Base exception class with telemetry integration
- **GlobalExceptionHandler**: Central exception handler for API errors
- **ResourceNotFoundException**: Exception for resource not found errors
- **ValidationException**: Exception for validation errors
- **BusinessRuleViolationException**: Exception for business rule violations

All exceptions follow a standardized format and inherit from BaseAppInsightsException:
```json
{
  "message": "ExceptionType: Detailed error message",
  "error": "Error type",
  "errorCode": "HTTP status code"
}
```

Exceptions are handled by the GlobalExceptionHandler class, which tracks them in Application Insights and returns appropriate error responses.

### BaseAppInsightsException
BaseAppInsightsException is a base exception class that provides telemetry integration for exceptions. It tracks the exception in Application Insights and returns a standardized error response.

### GlobalExceptionHandler
GlobalExceptionHandler is a central exception handler for API errors. It captures all exceptions and logs them to Application Insights with relevant context. It returns appropriate error responses based on the exception type.

### ResourceNotFoundException
ResourceNotFoundException is an exception thrown when a requested resource is not found. It inherits from BaseAppInsightsException and provides additional context for the resource type and ID.

### ValidationException
ValidationException is an exception thrown when a validation error occurs. It inherits from BaseAppInsightsException and provides additional context for the field and validation error message.

### BusinessRuleViolationException
BusinessRuleViolationException is an exception thrown when a business rule violation occurs. It inherits from BaseAppInsightsException and provides additional context for the rule and message.





## Contributors
Priyonuj Dey (priyonujdey.in)

