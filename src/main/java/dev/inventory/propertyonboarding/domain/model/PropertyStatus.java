package dev.inventory.propertyonboarding.domain.model;

/**
 * Represents the lifecycle status of a PropertyUnit within the Inventory context.
 */
public enum PropertyStatus {
    PENDING_SURVEY,
    SURVEYED,
    PRICE_SETTLED,
    REGISTERED,
    AVAILABLE_FOR_SALE
}
