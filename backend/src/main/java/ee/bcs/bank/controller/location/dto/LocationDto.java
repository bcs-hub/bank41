package ee.bcs.bank.controller.location.dto;

import ee.bcs.bank.controller.common.dto.TransactionTypeDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    private Integer cityId;

    @NotNull
    @Size(max = 255)
    private String locationName;

    @NotNull
    @Min(value = 1, message = "Vähemalt üks atm peab olema!")
    private Integer numberOfAtms;

    @NotNull
    private String imageData;

    private BigDecimal lng;
    private BigDecimal lat;

    @NotNull
    private List<TransactionTypeDto> transactionTypes;
}