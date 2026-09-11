package ee.bcs.bank.service;

import ee.bcs.bank.persistence.location.Location;
import ee.bcs.bank.persistence.location.LocationDto;
import ee.bcs.bank.persistence.location.LocationMapper;
import ee.bcs.bank.persistence.location.LocationRepository;
import ee.bcs.bank.persistence.location.LocationTransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static ee.bcs.bank.Status.STATUS_ACTIVE;

//teenus tyypi class

@Service
@RequiredArgsConstructor //

public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationTransactionTypeRepository locationTransactionTypeRepository;
    private final LocationMapper locationMapper;

    public List<LocationDto> findAtmLocations(Integer cityId) {
        List<Location> locations = locationRepository.findLocationsBy(cityId, STATUS_ACTIVE.getCode());

        return locations.stream().map(location -> locationMapper.toLocationDto(
                        location,
                        locationTransactionTypeRepository.findLocationTransactionTypesBy(location.getId())))
                .toList();
    }
}
