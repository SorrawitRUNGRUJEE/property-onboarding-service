package dev.inventory.propertyonboarding.domain.model;

import java.util.Objects;

/**
 * Value Object representing the physical address of a property.
 * Immutable — equality is based on attribute values, not identity.
 */
public class Address {

    private final String province;
    private final String district;
    private final String subDistrict;
    private final String postalCode;
    private final String detail;

    public Address(String province, String district, String subDistrict,
                   String postalCode, String detail) {
        if (province == null || province.isBlank()) {
            throw new IllegalArgumentException("Province is required");
        }
        if (district == null || district.isBlank()) {
            throw new IllegalArgumentException("District is required");
        }
        this.province = province;
        this.district = district;
        this.subDistrict = subDistrict;
        this.postalCode = postalCode;
        this.detail = detail;
    }

    public String getProvince()    { return province; }
    public String getDistrict()    { return district; }
    public String getSubDistrict() { return subDistrict; }
    public String getPostalCode()  { return postalCode; }
    public String getDetail()      { return detail; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address that)) return false;
        return Objects.equals(province, that.province)
            && Objects.equals(district, that.district)
            && Objects.equals(subDistrict, that.subDistrict)
            && Objects.equals(postalCode, that.postalCode)
            && Objects.equals(detail, that.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(province, district, subDistrict, postalCode, detail);
    }

    @Override
    public String toString() {
        return detail + ", " + subDistrict + ", " + district + ", " + province + " " + postalCode;
    }
}
