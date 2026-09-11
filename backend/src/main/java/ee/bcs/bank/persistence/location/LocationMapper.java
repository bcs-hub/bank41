package ee.bcs.bank.persistence.location;

import ee.bcs.bank.controller.location.dto.LocationInfo;
import org.mapstruct.*;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {


   @Mapping(source = "id", target = "locationId")
   @Mapping(source = "city.name", target = "cityName")
   @Mapping(source = "name", target = "locationName")
    LocationInfo toLocationInfoDto(Location location);



    List <LocationInfo> toLocationInfos(List <Location> location);

}