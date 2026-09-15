package ee.bcs.bank.service;

import ee.bcs.bank.controller.common.dto.TransactionTypeDto;
import ee.bcs.bank.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.bank.persistence.transactiontype.TransactionType;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeMapper;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionTypeServiceTest {

    @Mock
    private TransactionTypeRepository transactionTypeRepository;

    @Mock
    private TransactionTypeMapper transactionTypeMapper;

    @InjectMocks
    private TransactionTypeService transactionTypeService;

    @Test
    void findTransactionTypes_returnsAllTransactionTypesAsDtos() {
        TransactionType transactionType = new TransactionType();
        transactionType.setId(1);
        transactionType.setName("raha sisse");
        List<TransactionType> transactionTypes = List.of(transactionType);

        TransactionTypeDto transactionTypeDto = new TransactionTypeDto(1, "raha sisse", false);
        List<TransactionTypeDto> transactionTypeDtos = List.of(transactionTypeDto);

        when(transactionTypeRepository.findAll()).thenReturn(transactionTypes);
        when(transactionTypeMapper.toTransactionTypeDtos(transactionTypes)).thenReturn(transactionTypeDtos);

        List<TransactionTypeDto> result = transactionTypeService.findTransactionTypes();

        assertThat(result).isEqualTo(transactionTypeDtos);
    }

    @Test
    void findTransactionTypes_returnsEmptyListWhenNoneExist() {
        when(transactionTypeRepository.findAll()).thenReturn(List.of());
        when(transactionTypeMapper.toTransactionTypeDtos(List.of())).thenReturn(List.of());

        List<TransactionTypeDto> result = transactionTypeService.findTransactionTypes();

        assertThat(result).isEmpty();
    }

    @Test
    void getValidTransactionType_returnsTransactionTypeWhenFound() {
        TransactionType transactionType = new TransactionType();
        transactionType.setId(1);
        transactionType.setName("raha sisse");

        when(transactionTypeRepository.findById(1)).thenReturn(Optional.of(transactionType));

        TransactionType result = transactionTypeService.getValidTransactionType(1);

        assertThat(result).isEqualTo(transactionType);
    }

    @Test
    void getValidTransactionType_throwsExceptionWhenNotFound() {
        when(transactionTypeRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionTypeService.getValidTransactionType(99))
                .isInstanceOf(PrimaryKeyNotFoundException.class);
    }

}
