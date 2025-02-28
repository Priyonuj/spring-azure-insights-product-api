package com.nexacloud.demoappinsights.configuration;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.extensibility.TelemetryInitializer;
import com.microsoft.applicationinsights.telemetry.Telemetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class is responsible for initializing the Application Insights SDK
 * @author Priyonuj Dey
 */
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
    }}