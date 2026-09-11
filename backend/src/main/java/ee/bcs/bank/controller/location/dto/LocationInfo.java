package ee.bcs.bank.controller.location.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ee.bcs.bank.persistence.location.Location}
 */
@Value
public class LocationInfo implements Serializable {
    Integer locationId;
    String cityName;
    String locationName;
}