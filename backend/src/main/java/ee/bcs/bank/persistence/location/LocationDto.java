package ee.bcs.bank.persistence.location;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Value
public class LocationDto implements Serializable {
    Integer locationId;
    String locationName;
    String cityName;
    BigDecimal lng;
    BigDecimal lat;
    List<LocationTransactionTypeDto> transactionTypes;
}
