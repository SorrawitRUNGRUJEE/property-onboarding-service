package dev.inventory.propertyonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Property Onboarding Microservice — Inventory & Catalog Bounded Context.
 *
 * This service handles the formal registration of real estate units
 * and publishes PropertyRegistered events to Kafka for downstream
 * consumers (Marketing, Sales).
 */
@SpringBootApplication
public class PropertyOnboardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyOnboardingApplication.class, args);
    }
}
