package ee.bcs.bank.persistence.location;

import ee.bcs.bank.Status;
import ee.bcs.bank.controller.location.dto.AtmLocationDetailDto;
import ee.bcs.bank.controller.location.dto.LocationDto;
import ee.bcs.bank.controller.location.dto.LocationInfo;
import org.mapstruct.*;

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

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "name", target = "locationName")
    @Mapping(source = "numberOfAtms", target = "numberOfAtms")
    @Mapping(ignore = true, target = "imageData")
    @Mapping(source = "lng", target = "lng")
    @Mapping(source = "lat", target = "lat")
    @Mapping(ignore = true, target = "transactionTypes")
    LocationDto toLocationDto(Location location);


    @InheritConfiguration(name = "toLocation")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Location partialUpdate(LocationDto locationDto, @MappingTarget Location location);


}