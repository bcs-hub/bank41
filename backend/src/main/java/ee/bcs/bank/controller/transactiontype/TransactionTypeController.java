package ee.bcs.bank.controller.transactiontype;

import ee.bcs.bank.controller.common.dto.TransactionTypeDto;
import ee.bcs.bank.service.TransactionTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionTypeController {

    private final TransactionTypeService transactionTypeService;

    @GetMapping("/api/atm/transaction-types")
    @Operation(summary = "Leiab süsteemist kõik tehingutüübid")
    public List<TransactionTypeDto> findTransactionTypes() {
        List<TransactionTypeDto> transactionTypeDtos = transactionTypeService.findTransactionTypes();
        return transactionTypeDtos;
    }

}
