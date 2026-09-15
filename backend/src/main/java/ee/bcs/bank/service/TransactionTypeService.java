package ee.bcs.bank.service;

import ee.bcs.bank.controller.common.dto.TransactionTypeDto;
import ee.bcs.bank.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.bank.persistence.transactiontype.TransactionType;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeMapper;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionTypeService {

    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionTypeMapper transactionTypeMapper;

    public List<TransactionTypeDto> findTransactionTypes() {
        List<TransactionType> transactionTypes = transactionTypeRepository.findAll();
        List<TransactionTypeDto> transactionTypeDtos = transactionTypeMapper.toTransactionTypeDtos(transactionTypes);
        return transactionTypeDtos;
    }

    public TransactionType getValidTransactionType(Integer transactionTypeId) {
        TransactionType transactionType = transactionTypeRepository.findById(transactionTypeId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("transactionTypeId", transactionTypeId));
        return transactionType;
    }
}
