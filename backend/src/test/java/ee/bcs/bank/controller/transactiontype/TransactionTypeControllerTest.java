package ee.bcs.bank.controller.transactiontype;

import ee.bcs.bank.controller.common.dto.TransactionTypeDto;
import ee.bcs.bank.service.TransactionTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionTypeControllerTest {

    @Mock
    private TransactionTypeService transactionTypeService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionTypeController(transactionTypeService)).build();
    }

    @Test
    void findTransactionTypes_returns200WithTransactionTypes() throws Exception {
        List<TransactionTypeDto> transactionTypeDtos = List.of(
                new TransactionTypeDto(1, "raha sisse", false),
                new TransactionTypeDto(2, "raha välja", false),
                new TransactionTypeDto(3, "maksed", false)
        );
        when(transactionTypeService.findTransactionTypes()).thenReturn(transactionTypeDtos);

        mockMvc.perform(get("/api/atm/transaction-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].transactionTypeId").value(1))
                .andExpect(jsonPath("$[0].transactionTypeName").value("raha sisse"))
                .andExpect(jsonPath("$[0].isAvailable").value(false));
    }

    @Test
    void findTransactionTypes_returns200WithEmptyArrayWhenNoneExist() throws Exception {
        when(transactionTypeService.findTransactionTypes()).thenReturn(List.of());

        mockMvc.perform(get("/api/atm/transaction-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
