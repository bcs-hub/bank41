package ee.bcs.bank.controller.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTypeDto implements Serializable {

    private Integer transactionTypeId;
    private String transactionTypeName;
    private boolean isAvailable;


}
