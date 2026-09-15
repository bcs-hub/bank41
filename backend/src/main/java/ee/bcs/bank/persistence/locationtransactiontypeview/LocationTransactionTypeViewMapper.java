package ee.bcs.bank.persistence.locationtransactiontypeview;

import ee.bcs.bank.controller.common.dto.TransactionTypeDto;
import ee.bcs.bank.controller.location.dto.LocationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationTransactionTypeViewMapper {

    @Mapping(source = "id.locationId", target = "locationId")
    @Mapping(source = "cityName", target = "cityName")
    @Mapping(source = "locationName", target = "locationName")
    @Mapping(source = "lng", target = "lng")
    @Mapping(source = "lat", target = "lat")
    LocationInfo toLocationInfo(LocationTransactionTypeView locationTransactionTypeView);

    @Mapping(source = "id.transactionTypeId", target = "transactionTypeId")
    @Mapping(source = "transactionTypeName", target = "transactionTypeName")
    @Mapping(source = "isAvailable", target = "isAvailable")
    TransactionTypeDto toTransactionTypeDto(LocationTransactionTypeView locationTransactionTypeView);

}
