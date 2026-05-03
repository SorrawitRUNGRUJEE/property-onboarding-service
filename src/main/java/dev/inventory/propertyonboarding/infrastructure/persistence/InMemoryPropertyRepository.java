package dev.inventory.propertyonboarding.infrastructure.persistence;

import dev.inventory.propertyonboarding.domain.model.PropertyUnit;
import dev.inventory.propertyonboarding.domain.repository.PropertyRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of PropertyRepository.
 * For demo/testing purposes — in production this would be replaced
 * with a JPA/SQL adapter.
 */
@Repository
public class InMemoryPropertyRepository implements PropertyRepository {

    private final Map<String, PropertyUnit> store = new ConcurrentHashMap<>();

    @Override
    public PropertyUnit save(PropertyUnit unit) {
        store.put(unit.getPropertyId(), unit);
        return unit;
    }

    @Override
    public Optional<PropertyUnit> findById(String propertyId) {
        return Optional.ofNullable(store.get(propertyId));
    }

    @Override
    public List<PropertyUnit> findAll() {
        return new ArrayList<>(store.values());
    }
}
