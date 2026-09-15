package ee.bcs.bank.service;

import ee.bcs.bank.controller.location.dto.LocationDto;
import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.controller.location.dto.TransactionTypeDto;
import ee.bcs.bank.infrastructure.exception.DataNotFoundException;
import ee.bcs.bank.infrastructure.exception.ForbiddenException;
import ee.bcs.bank.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.bank.persistence.city.City;
import ee.bcs.bank.persistence.city.CityRepository;
import ee.bcs.bank.persistence.location.Location;
import ee.bcs.bank.persistence.location.LocationMapper;
import ee.bcs.bank.persistence.location.LocationRepository;
import ee.bcs.bank.persistence.locationimage.LocationImage;
import ee.bcs.bank.persistence.locationimage.LocationImageMapper;
import ee.bcs.bank.persistence.locationimage.LocationImageRepository;
import ee.bcs.bank.persistence.locationtransactiontype.LocationTransactionType;
import ee.bcs.bank.persistence.locationtransactiontype.LocationTransactionTypeRepository;
import ee.bcs.bank.persistence.transactiontype.TransactionType;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeMapper;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static ee.bcs.bank.Error.LOCATION_UNAVAILABLE;
import static ee.bcs.bank.Error.NO_LOCATION_FOUND;
import static ee.bcs.bank.Status.STATUS_ACTIVE;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionTypeMapper transactionTypeMapper;
    private final LocationTransactionTypeRepository locationTransactionTypeRepository;
    private final CityRepository cityRepository;
    private final LocationImageRepository locationImageRepository;
    private final LocationImageMapper locationImageMapper;
    private final CityService cityService;
    private final TransactionTypeService transactionTypeService;

    @Transactional
    public void addLocation(LocationDto locationDto) {
        validateLocationNameIsAvailable(locationDto.getLocationName());
        Location location = createAndSaveLocation(locationDto);
        handleCreateAndSaveLocationImage(locationDto, location);
        handleCreateAndSaveLocationTransactionTypes(locationDto, location);
    }

    private void handleCreateAndSaveLocationTransactionTypes(LocationDto locationDto, Location location) {
        List<LocationTransactionType> locationTransactionTypes = createLocationTransactionTypes(locationDto, location);
        locationTransactionTypeRepository.saveAll(locationTransactionTypes);
    }

    private @NonNull List<LocationTransactionType> createLocationTransactionTypes(LocationDto locationDto, Location location) {
        List<LocationTransactionType> locationTransactionTypes = new ArrayList<>();
        for (TransactionTypeDto transactionTypeDto : locationDto.getTransactionTypes()) {
            if (transactionTypeDto.getIsAvailable()) {

                Integer transactionTypeId = transactionTypeDto.getTransactionTypeId();

                TransactionType transactionType = transactionTypeService.getValidTransactionType(transactionTypeId);
                LocationTransactionType locationTransactionType = new LocationTransactionType();
                locationTransactionType.setLocation(location);
                locationTransactionType.setTransactionType(transactionType);
                locationTransactionTypes.add(locationTransactionType);
            }
        }
        return locationTransactionTypes;
    }



    private void handleCreateAndSaveLocationImage(LocationDto locationDto, Location location) {
        String imageDataAsString = locationDto.getImageData();
        if (!imageDataAsString.isEmpty()) {
            LocationImage locationImage = createLocationImage(locationDto, location);
            locationImageRepository.save(locationImage);
        }
    }

    private LocationImage createLocationImage(LocationDto locationDto, Location location) {
        LocationImage locationImage = locationImageMapper.toLocationImage(locationDto);
        locationImage.setLocation(location);
        return locationImage;
    }

    private Location createAndSaveLocation(LocationDto locationDto) {
        Location location = createLocation(locationDto);
        locationRepository.save(location);
        return location;
    }

    private Location createLocation(LocationDto locationDto) {
        City city = cityService.getValidCity(locationDto.getCityId());
        Location location = locationMapper.toLocation(locationDto);
        location.setCity(city);
        locationRepository.save(location);
        return location;
    }

    private void validateLocationNameIsAvailable(String locationName) {
        boolean locationExists = locationRepository.locationExistsBy(locationName);
        if (locationExists) {
            throw new ForbiddenException(LOCATION_UNAVAILABLE.getMessage(), LOCATION_UNAVAILABLE.name());
        }
    }


    public List<LocationInfo> findAtmLocations(Integer cityId) {
        List<Location> locations = locationRepository.findFilteredLocationsBy(cityId, STATUS_ACTIVE.getCode());
        validateAtLeastOneLocationExists(locations);
        List<LocationInfo> locationInfos = locationMapper.toLocationInfos(locations);
        addTransactionTypes(locationInfos);
        return locationInfos;
    }

    private void addTransactionTypes(List<LocationInfo> locationInfos) {
        for (LocationInfo locationInfo : locationInfos) {
            List<TransactionTypeDto> transactionTypeDtos = createTransactionTypeDtos(locationInfo);
            locationInfo.setTransactionTypes(transactionTypeDtos);
        }
    }

    private List<TransactionTypeDto> createTransactionTypeDtos(LocationInfo locationInfo) {
        Sort byNameDesc = Sort.by(Sort.Direction.DESC, "name");
        List<TransactionType> transactionTypes = transactionTypeRepository.findAll(byNameDesc);
        List<TransactionTypeDto> transactionTypeDtos = transactionTypeMapper.toTransactionTypeDtos(transactionTypes);

        for (TransactionTypeDto transactionTypeDto : transactionTypeDtos) {
            boolean locationTransactionTypeExists = locationTransactionTypeRepository.locationTransactionTypeExistsBy(locationInfo.getLocationId(), transactionTypeDto.getTransactionTypeId());
            transactionTypeDto.setIsAvailable(locationTransactionTypeExists);
        }
        return transactionTypeDtos;
    }

    private static void validateAtLeastOneLocationExists(List<Location> locations) {
        if (locations.isEmpty()) {
            throw new DataNotFoundException(NO_LOCATION_FOUND.getMessage(), NO_LOCATION_FOUND.name());
        }
    }

}
