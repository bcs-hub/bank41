package ee.bcs.bank.persistence.transactiontype;

import ee.bcs.bank.controller.location.dto.TransactionTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionTypeMapper {


    @Mapping(source = "id", target = "transactionTypeId")
    @Mapping(source = "name", target = "transactionTypeName")
    @Mapping(constant = "false", target = "isAvailable")
    TransactionTypeDto toTransactionTypeDto(TransactionType transactionType);

}