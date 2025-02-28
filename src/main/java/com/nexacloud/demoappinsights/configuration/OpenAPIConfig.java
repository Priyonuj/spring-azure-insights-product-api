package com.nexacloud.demoappinsights.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configures OpenAPI (Swagger) documentation for the application.
 * This class sets up API metadata, server information, and endpoint grouping
 * to generate interactive documentation for the Product API.
 *
 * @author Priyonuj Dey
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Defines the OpenAPI configuration bean.
     * This method sets up the basic information about the API, including title, version,
     * description, contact info, and server details.
     *
     * @return OpenAPI object containing the API configuration
     */
    @Bean
    public OpenAPI defineOpenApi() {
        // Define a server URL relative to the application context path
        Server relativeServer = new Server()
                .url("/")
                .description("Current Server");

        // Create an Info object to define the API metadata
        Info info = new Info()
                .title("Product Management API")
                .version("1.0")
                .description("""
                REST API for managing product data with Azure Application Insights integration.
                
                This API provides endpoints for creating, retrieving, updating, and deleting product information.
                All operations are monitored with Azure Application Insights for performance tracking and diagnostics.
                
                The API includes:
                - Product listing with optional price filtering
                - Individual product retrieval
                - Product creation and updates
                - Product deletion
                """);

        // Return a new OpenAPI object with the configured Info
        return new OpenAPI().servers(List.of(relativeServer)).info(info);
    }

    /**
     * Configures the public API group.
     * This method defines which endpoints are included in the API documentation,
     * organizing them under a "public" group for better readability.
     *
     * @return GroupedOpenApi object containing path configurations
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")  // Include all endpoints in the documentation
                .build();
    }

}
