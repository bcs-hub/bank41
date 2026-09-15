package ee.bcs.bank.persistence.location;

import ee.bcs.bank.Status;
import ee.bcs.bank.controller.location.dto.LocationDto;
import ee.bcs.bank.controller.location.dto.LocationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, imports = {Status.class})
public interface LocationMapper {


    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "city")
    @Mapping(source = "locationName", target = "name")
    @Mapping(source = "numberOfAtms", target = "numberOfAtms")
    @Mapping(expression = "java(Status.STATUS_ACTIVE.getCode())", target = "status")
    @Mapping(source = "lng", target = "lng")
    @Mapping(source = "lat", target = "lat")
    Location toLocation(LocationDto locationDto);


    @Mapping(source = "id", target = "locationId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "name", target = "locationName")
    @Mapping(source = "lng", target = "lng")
    @Mapping(source = "lat", target = "lat")
    LocationInfo toLocationInfo(Location location);


    List<LocationInfo> toLocationInfos(List<Location> locations);


}