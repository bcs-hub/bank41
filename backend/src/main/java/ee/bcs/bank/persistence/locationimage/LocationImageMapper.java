package ee.bcs.bank.persistence.locationimage;

import ee.bcs.bank.controller.location.dto.LocationDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationImageMapper {


    LocationImage toLocationImage(LocationDto locationDto);

}