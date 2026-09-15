package ee.bcs.bank.persistence.locationimage;

import ee.bcs.bank.controller.location.dto.LocationDto;
import ee.bcs.bank.infrastructure.util.StringBytesConverter;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationImageMapper {


    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "location")
    @Mapping(source = "imageData", target = "data", qualifiedByName = "toBytes")
    LocationImage toLocationImage(LocationDto locationDto);

    @Named("toBytes")
    static byte[] toBytes(String value){
        byte[] bytes = StringBytesConverter.stringToBytes(value);
        return bytes;
    }


}