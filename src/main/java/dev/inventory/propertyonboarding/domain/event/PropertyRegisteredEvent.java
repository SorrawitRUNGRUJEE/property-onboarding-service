package dev.inventory.propertyonboarding.domain.event;

import java.math.BigDecimal;

/**
 * Domain Event: Property Registered (past tense, per DDD convention).
 *
 * This event is published when a new property unit has been formally
 * onboarded into the Inventory system after CEO approval and price settlement.
 *
 * Downstream consumers (Marketing, Sales) use this event to begin
 * their own workflows (e.g. advertisement announcement, quotation prep).
 *
 * Only exposes the data needed by consumers — the internal aggregate
 * structure is hidden (Information Hiding principle).
 */
public class PropertyRegisteredEvent {

    private String propertyId;
    private String projectName;
    private String unitNumber;
    private String propertyType;
    private String address;
    private BigDecimal settledPrice;
    private double areaSqm;
    private String registeredAt;

    // Default constructor required for JSON deserialization
    public PropertyRegisteredEvent() {}

    public PropertyRegisteredEvent(String propertyId, String projectName,
                                    String unitNumber, String propertyType,
                                    String address, BigDecimal settledPrice,
                                    double areaSqm, String registeredAt) {
        this.propertyId   = propertyId;
        this.projectName  = projectName;
        this.unitNumber   = unitNumber;
        this.propertyType = propertyType;
        this.address      = address;
        this.settledPrice = settledPrice;
        this.areaSqm      = areaSqm;
        this.registeredAt = registeredAt;
    }

    // --- Getters and Setters (needed for Jackson serialization) ---
    public String getPropertyId()          { return propertyId; }
    public void setPropertyId(String v)    { this.propertyId = v; }

    public String getProjectName()         { return projectName; }
    public void setProjectName(String v)   { this.projectName = v; }

    public String getUnitNumber()          { return unitNumber; }
    public void setUnitNumber(String v)    { this.unitNumber = v; }

    public String getPropertyType()        { return propertyType; }
    public void setPropertyType(String v)  { this.propertyType = v; }

    public String getAddress()             { return address; }
    public void setAddress(String v)       { this.address = v; }

    public BigDecimal getSettledPrice()    { return settledPrice; }
    public void setSettledPrice(BigDecimal v) { this.settledPrice = v; }

    public double getAreaSqm()             { return areaSqm; }
    public void setAreaSqm(double v)       { this.areaSqm = v; }

    public String getRegisteredAt()        { return registeredAt; }
    public void setRegisteredAt(String v)  { this.registeredAt = v; }

    @Override
    public String toString() {
        return "PropertyRegisteredEvent{" +
               "propertyId='" + propertyId + '\'' +
               ", projectName='" + projectName + '\'' +
               ", unitNumber='" + unitNumber + '\'' +
               ", propertyType='" + propertyType + '\'' +
               ", address='" + address + '\'' +
               ", settledPrice=" + settledPrice +
               ", areaSqm=" + areaSqm +
               ", registeredAt='" + registeredAt + '\'' +
               '}';
    }
}
