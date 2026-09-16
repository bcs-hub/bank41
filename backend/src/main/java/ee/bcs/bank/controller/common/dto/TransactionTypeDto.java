package ee.bcs.bank.controller.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTypeDto implements Serializable {
    @NotNull
    private Integer transactionTypeId;
    private String transactionTypeName;
    @NotNull
    private Boolean isAvailable;
}
