package ee.bcs.bank.persistence.transactiontype;

import ee.bcs.bank.controller.location.dto.TransactionTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionTypeMapper {

    @Mapping(source = "id", target = "transactionTypeId")
    @Mapping(source = "name", target = "transactionTypeName")
    @Mapping(constant = "false", target = "available")
    TransactionTypeDto toTransactionTypeDto(TransactionType transactionType);


    List<TransactionTypeDto> toTransactionTypeDtos(List<TransactionType> transactionTypes);

}