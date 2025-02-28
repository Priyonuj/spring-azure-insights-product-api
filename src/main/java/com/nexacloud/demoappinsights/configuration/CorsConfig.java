package com.nexacloud.demoappinsights.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to set up Cross-Origin Resource Sharing (CORS) policies.
 * This allows the server to specify which origins, HTTP methods, and headers are allowed.
 *
 * @author Priyonuj Dey
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures CORS settings for the application.
     *
     * @param registry CorsRegistry to which CORS configuration is added
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Apply CORS configuration to all paths
                .allowedOrigins("*")  // Allow requests from any origin
                .allowedMethods("GET", "POST", "PUT")  // Allow these HTTP methods
                .allowedHeaders("*")  // Allow all headers in requests
                .allowCredentials(false)  // Don't allow credentials (cookies, auth headers)
                .maxAge(3600);  // Cache preflight response for 1 hour (3600 seconds)
    }
}