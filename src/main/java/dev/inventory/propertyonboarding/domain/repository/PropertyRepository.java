package dev.inventory.propertyonboarding.domain.repository;

import dev.inventory.propertyonboarding.domain.model.PropertyUnit;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface (Port) defined in the Domain layer.
 * The Infrastructure layer provides the concrete implementation.
 */
public interface PropertyRepository {

    PropertyUnit save(PropertyUnit unit);

    Optional<PropertyUnit> findById(String propertyId);

    List<PropertyUnit> findAll();
}
