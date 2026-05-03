package dev.inventory.propertyonboarding.domain.model;

import dev.inventory.propertyonboarding.domain.event.PropertyRegisteredEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root for the Property Onboarding bounded context.
 *
 * Business invariants enforced:
 * - A property cannot be registered without a settled price (price > 0).
 * - A property must have a valid address.
 * - Each property is uniquely identified by a system-generated PropertyID.
 */
public class PropertyUnit {

    private String propertyId;
    private String projectName;
    private String unitNumber;
    private String propertyType;   // e.g. "Condo", "House", "Townhouse"
    private Address address;
    private BigDecimal settledPrice;
    private double areaSqm;
    private PropertyStatus status;
    private LocalDateTime registeredAt;

    /**
     * Factory method — creates a new PropertyUnit and enforces all invariants.
     * Returns a domain event if registration is successful.
     */
    public static RegistrationResult register(String projectName,
                                               String unitNumber,
                                               String propertyType,
                                               Address address,
                                               BigDecimal settledPrice,
                                               double areaSqm) {
        // ---- Business rule: price must be settled ----
        if (settledPrice == null || settledPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "Cannot register a property without a settled price (must be > 0)");
        }

        // ---- Business rule: address is mandatory ----
        if (address == null) {
            throw new IllegalArgumentException("Address is required for registration");
        }

        // ---- Business rule: unit number is mandatory ----
        if (unitNumber == null || unitNumber.isBlank()) {
            throw new IllegalArgumentException("Unit number is required");
        }

        // Create the aggregate
        PropertyUnit unit = new PropertyUnit();
        unit.propertyId   = UUID.randomUUID().toString();
        unit.projectName  = projectName;
        unit.unitNumber   = unitNumber;
        unit.propertyType = propertyType;
        unit.address      = address;
        unit.settledPrice = settledPrice;
        unit.areaSqm      = areaSqm;
        unit.status       = PropertyStatus.REGISTERED;
        unit.registeredAt = LocalDateTime.now();

        // Produce domain event
        PropertyRegisteredEvent event = new PropertyRegisteredEvent(
            unit.propertyId,
            unit.projectName,
            unit.unitNumber,
            unit.propertyType,
            unit.address.toString(),
            unit.settledPrice,
            unit.areaSqm,
            unit.registeredAt.toString()
        );

        return new RegistrationResult(unit, event);
    }

    // --- Getters ---
    public String getPropertyId()       { return propertyId; }
    public String getProjectName()      { return projectName; }
    public String getUnitNumber()       { return unitNumber; }
    public String getPropertyType()     { return propertyType; }
    public Address getAddress()         { return address; }
    public BigDecimal getSettledPrice() { return settledPrice; }
    public double getAreaSqm()          { return areaSqm; }
    public PropertyStatus getStatus()   { return status; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }

    /**
     * Holds both the persisted aggregate and the domain event to be published.
     */
    public record RegistrationResult(PropertyUnit unit, PropertyRegisteredEvent event) {}
}
