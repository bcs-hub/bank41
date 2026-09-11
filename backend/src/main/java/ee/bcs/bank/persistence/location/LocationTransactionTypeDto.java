package ee.bcs.bank.persistence.location;

import lombok.Value;

import java.io.Serializable;

@Value
public class LocationTransactionTypeDto implements Serializable {
    Integer transactionTypeId;
    String transactionTypeName;
    Boolean isAvailable;
}
