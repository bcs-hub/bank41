package ee.bcs.bank.persistence.city;

import ee.bcs.bank.controller.city.dto.CityDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityMapper {

    @Mapping(source = "id", target = "cityId")
    @Mapping(source = "name", target = "cityName")

    CityDto toCityDto(City city);

}