package ee.bcs.bank.service;

import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.infrastructure.exception.DataNotFoundException;
import ee.bcs.bank.persistence.location.Location;
import ee.bcs.bank.persistence.location.LocationMapper;
import ee.bcs.bank.persistence.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static ee.bcs.bank.Error.NO_LOCATION_FOUND;
import static ee.bcs.bank.Status.STATUS_ACTIVE;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public List<LocationInfo> findAtmLocations(Integer cityId) {
        List<Location> locations = locationRepository.findFilteredLocationsBy(cityId, STATUS_ACTIVE.getCode());
        if (locations.isEmpty()) {
            throw new DataNotFoundException(NO_LOCATION_FOUND.getMessage(), NO_LOCATION_FOUND.name());
        }
        List<LocationInfo> locationInfos = locationMapper.toLocationInfos(locations);
        return locationInfos;
    }


}
