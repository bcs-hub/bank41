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


    // Uue asukoha loomine LocationDto pealt (kasutab POST /api/atm/location endpoint).
    // id jäetakse teadlikult mappimata (ignore = true) — andmebaas genereerib selle ise
    // (@GeneratedValue Location entiteedis).
    // city jäetakse samuti mappimata, kuna DTO-s on ainult cityId (number), mitte terve
    // City objekt — seose panemine cityId järgi tuleb teha eraldi service kihis.
    // status pannakse otse "A" (aktiivne) väärtuseks java expression'iga, kuna DTO-s
    // seda välja üldse ei ole — uus asukoht luuakse alati aktiivsena.
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