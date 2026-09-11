package ee.bcs.bank.persistence.city;

import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityMapper {

    //target

    @Mapping(source = "id", target = "cityId")
    @Mapping(source = "name", target = "cityName")
    CityDto toCityDto(City city);





   List <CityDto> toCityDtos(List <City> cities);


}