package ee.bcs.bank.service;

import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.infrastructure.exception.DataNotFoundException;
import ee.bcs.bank.persistence.location.Location;
import ee.bcs.bank.persistence.location.LocationMapper;
import ee.bcs.bank.persistence.location.LocationRepository;
import ee.bcs.bank.persistence.transactiontype.TransactionType;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static ee.bcs.bank.Error.NO_LOCATION_FOUND;
import static ee.bcs.bank.Status.STATUS_ACTIVE;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final TransactionTypeRepository transactionTypeRepository;

    public List<LocationInfo> findAtmLocations(Integer cityId) {
        List<Location> locations = locationRepository.findFilteredLocationsBy(cityId, STATUS_ACTIVE.getCode());
        if (locations.isEmpty()) {
            throw new DataNotFoundException(NO_LOCATION_FOUND.getMessage(), NO_LOCATION_FOUND.name());
        }
        List<LocationInfo> locationInfos = locationMapper.toLocationInfos(locations);


//
//        {
//                "locationId": 1,
//                "cityName": "Tallinn",
//                "locationName": "Sikupilli Prisma",
//                "lng": 24.7795,
//                "lat": 59.4369,
//                "transactionTypes": null
//        }


        for (LocationInfo locationInfo : locationInfos) {

            Sort byNameDesc = Sort.by(Sort.Direction.DESC, "name");

            List<TransactionType> transactionTypes = transactionTypeRepository.findAll(byNameDesc);
        }


        return locationInfos;
    }


}
