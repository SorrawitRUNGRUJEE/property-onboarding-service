package dev.inventory.propertyonboarding.presentation;

import java.math.BigDecimal;

/**
 * Inbound DTO for the REST API — maps the JSON request body
 * to a Java object before passing to the application layer.
 */
public class RegisterPropertyRequest {

    private String projectName;
    private String unitNumber;
    private String propertyType;
    private String province;
    private String district;
    private String subDistrict;
    private String postalCode;
    private String addressDetail;
    private BigDecimal settledPrice;
    private double areaSqm;

    // --- Getters & Setters ---
    public String getProjectName()     { return projectName; }
    public void setProjectName(String v) { this.projectName = v; }

    public String getUnitNumber()      { return unitNumber; }
    public void setUnitNumber(String v) { this.unitNumber = v; }

    public String getPropertyType()    { return propertyType; }
    public void setPropertyType(String v) { this.propertyType = v; }

    public String getProvince()        { return province; }
    public void setProvince(String v)  { this.province = v; }

    public String getDistrict()        { return district; }
    public void setDistrict(String v)  { this.district = v; }

    public String getSubDistrict()     { return subDistrict; }
    public void setSubDistrict(String v) { this.subDistrict = v; }

    public String getPostalCode()      { return postalCode; }
    public void setPostalCode(String v) { this.postalCode = v; }

    public String getAddressDetail()   { return addressDetail; }
    public void setAddressDetail(String v) { this.addressDetail = v; }

    public BigDecimal getSettledPrice() { return settledPrice; }
    public void setSettledPrice(BigDecimal v) { this.settledPrice = v; }

    public double getAreaSqm()         { return areaSqm; }
    public void setAreaSqm(double v)   { this.areaSqm = v; }
}
