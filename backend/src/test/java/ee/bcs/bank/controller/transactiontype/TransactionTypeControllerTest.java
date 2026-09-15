package ee.bcs.bank.controller.transactiontype;

import ee.bcs.bank.controller.common.dto.TransactionTypeDto;
import ee.bcs.bank.service.TransactionTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionTypeController.class)
class TransactionTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionTypeService transactionTypeService;

    @Test
    void findTransactionTypes_returnsAllTransactionTypes() throws Exception {
        List<TransactionTypeDto> transactionTypeDtos = List.of(
                new TransactionTypeDto(1, "raha sisse", false),
                new TransactionTypeDto(2, "raha välja", false),
                new TransactionTypeDto(3, "maksed", false)
        );
        when(transactionTypeService.findTransactionTypes()).thenReturn(transactionTypeDtos);

        mockMvc.perform(get("/api/atm/transaction-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].transactionTypeId").value(2))
                .andExpect(jsonPath("$[0].transactionTypeName").value("raha sisse"))
                .andExpect(jsonPath("$[0].isAvailable").value(false));
    }

    @Test
    void findTransactionTypes_returnsEmptyArray_whenNoTransactionTypesExist() throws Exception {
        when(transactionTypeService.findTransactionTypes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/atm/transaction-types"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}
