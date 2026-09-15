package ee.bcs.bank.service;

import ee.bcs.bank.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.bank.persistence.transactiontype.TransactionType;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionType getValidTransactionType(Integer transactionTypeId) {
        TransactionType transactionType = transactionTypeRepository.findById(transactionTypeId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("transactionTypeId", transactionTypeId));
        return transactionType;
    }}
