package dev.inventory.propertyonboarding.application;

import dev.inventory.propertyonboarding.domain.model.Address;
import dev.inventory.propertyonboarding.domain.model.PropertyUnit;
import dev.inventory.propertyonboarding.domain.model.PropertyUnit.RegistrationResult;
import dev.inventory.propertyonboarding.domain.repository.PropertyRepository;
import dev.inventory.propertyonboarding.infrastructure.kafka.PropertyEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Application Service — acts as the Orchestrator (Clean Architecture).
 *
 * Responsibilities:
 * 1. Receive the onboarding request from the presentation layer.
 * 2. Invoke the Aggregate's factory method to enforce business rules.
 * 3. Persist the aggregate via the repository port.
 * 4. Publish the domain event via the Kafka producer.
 *
 * This layer contains NO business logic — it only coordinates.
 */
@Service
public class RegisterPropertyService {

    private static final Logger log = LoggerFactory.getLogger(RegisterPropertyService.class);

    private final PropertyRepository propertyRepository;
    private final PropertyEventProducer eventProducer;

    public RegisterPropertyService(PropertyRepository propertyRepository,
                                    PropertyEventProducer eventProducer) {
        this.propertyRepository = propertyRepository;
        this.eventProducer = eventProducer;
    }

    /**
     * Handles the property registration use case.
     */
    public PropertyUnit registerProperty(String projectName,
                                          String unitNumber,
                                          String propertyType,
                                          String province,
                                          String district,
                                          String subDistrict,
                                          String postalCode,
                                          String addressDetail,
                                          BigDecimal settledPrice,
                                          double areaSqm) {

        log.info("Processing property registration: project={}, unit={}",
                 projectName, unitNumber);

        // 1. Build value objects
        Address address = new Address(province, district, subDistrict, postalCode, addressDetail);

        // 2. Invoke aggregate — business rules are checked here
        RegistrationResult result = PropertyUnit.register(
            projectName, unitNumber, propertyType, address, settledPrice, areaSqm
        );

        // 3. Persist the aggregate
        propertyRepository.save(result.unit());
        log.info("Property persisted with id={}", result.unit().getPropertyId());

        // 4. Publish domain event (async, nonblocking)
        eventProducer.publish(result.event());

        return result.unit();
    }

    /**
     * Query: retrieve all registered properties.
     */
    public List<PropertyUnit> getAllProperties() {
        return propertyRepository.findAll();
    }
}
