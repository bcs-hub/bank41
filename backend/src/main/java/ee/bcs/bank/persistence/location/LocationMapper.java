package ee.bcs.bank.persistence.location;

import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.name", target = "locationName")
    @Mapping(source = "location.city.name", target = "cityName")
    @Mapping(source = "transactionTypes", target = "transactionTypes")
    LocationDto toLocationDto(Location location, List<LocationTransactionTypeDto> transactionTypes);
}
