package ee.bcs.bank.controller.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link ee.bcs.bank.persistence.location.Location}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationInfo implements Serializable {
    private Integer locationId;
    private String cityName;
    private String locationName;
    private BigDecimal lng;
    private BigDecimal lat;
    private List<TransactionTypeDto> transactionTypes;

}