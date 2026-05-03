package dev.inventory.propertyonboarding.presentation;

import dev.inventory.propertyonboarding.application.RegisterPropertyService;
import dev.inventory.propertyonboarding.domain.model.PropertyUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Adapter (Presentation Layer).
 *
 * Exposes the onboarding endpoint to internal tools
 * (e.g. CEO approval / survey system).
 */
@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private final RegisterPropertyService registerPropertyService;

    public PropertyController(RegisterPropertyService registerPropertyService) {
        this.registerPropertyService = registerPropertyService;
    }

    /**
     * POST /api/v1/properties
     * Registers a new property unit into the system.
     * On success, a PropertyRegisteredEvent is published to Kafka.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> registerProperty(
            @RequestBody RegisterPropertyRequest request) {

        try {
            PropertyUnit unit = registerPropertyService.registerProperty(
                request.getProjectName(),
                request.getUnitNumber(),
                request.getPropertyType(),
                request.getProvince(),
                request.getDistrict(),
                request.getSubDistrict(),
                request.getPostalCode(),
                request.getAddressDetail(),
                request.getSettledPrice(),
                request.getAreaSqm()
            );

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "REGISTERED");
            response.put("propertyId", unit.getPropertyId());
            response.put("projectName", unit.getProjectName());
            response.put("unitNumber", unit.getUnitNumber());
            response.put("propertyType", unit.getPropertyType());
            response.put("settledPrice", unit.getSettledPrice());
            response.put("areaSqm", unit.getAreaSqm());
            response.put("registeredAt", unit.getRegisteredAt().toString());
            response.put("message", "Property registered successfully. Event published to Kafka.");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException ex) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", "ERROR");
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * GET /api/v1/properties
     * Returns all registered properties (for verification).
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllProperties() {
        List<Map<String, Object>> result = registerPropertyService.getAllProperties().stream()
            .map(unit -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("propertyId", unit.getPropertyId());
                map.put("projectName", unit.getProjectName());
                map.put("unitNumber", unit.getUnitNumber());
                map.put("propertyType", unit.getPropertyType());
                map.put("settledPrice", unit.getSettledPrice());
                map.put("status", unit.getStatus());
                return map;
            })
            .toList();
        return ResponseEntity.ok(result);
    }
}
