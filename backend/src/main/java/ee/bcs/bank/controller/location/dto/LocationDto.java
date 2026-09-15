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
public class LocationDto implements Serializable {
    private Integer cityId;
    private String locationName;
    private Integer numberOfAtms;
    private String imageData;
    private BigDecimal lng;
    private BigDecimal lat;
    private List<TransactionTypeDto> transactionTypes;
}