package ee.bcs.bank.service;

import ee.bcs.bank.Error;
import ee.bcs.bank.controller.location.dto.LocationDto;
import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.controller.location.dto.TransactionTypeDto;
import ee.bcs.bank.infrastructure.exception.DataNotFoundException;
import ee.bcs.bank.infrastructure.exception.ForbiddenException;
import ee.bcs.bank.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.bank.infrastructure.util.StringBytesConverter;
import ee.bcs.bank.persistence.city.City;
import ee.bcs.bank.persistence.city.CityRepository;
import ee.bcs.bank.persistence.location.Location;
import ee.bcs.bank.persistence.location.LocationMapper;
import ee.bcs.bank.persistence.location.LocationRepository;
import ee.bcs.bank.persistence.locationimage.LocationImage;
import ee.bcs.bank.persistence.locationimage.LocationImageMapper;
import ee.bcs.bank.persistence.locationimage.LocationImageRepository;
import ee.bcs.bank.persistence.locationtransasctiontype.LocationTransactionType;
import ee.bcs.bank.persistence.locationtransasctiontype.LocationTransactionTypeRepository;
import ee.bcs.bank.persistence.locationtransactiontypeview.LocationTransactionTypeView;
import ee.bcs.bank.persistence.locationtransactiontypeview.LocationTransactionTypeViewMapper;
import ee.bcs.bank.persistence.locationtransactiontypeview.LocationTransactionTypeViewRepository;
import ee.bcs.bank.persistence.transactiontype.TransactionType;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeMapper;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static ee.bcs.bank.Error.*;
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
    private final LocationTransactionTypeViewRepository locationTransactionTypeViewRepository;
    private final LocationTransactionTypeViewMapper locationTransactionTypeViewMapper;
    private final CityRepository cityRepository;
    private final LocationImageRepository locationImageRepository;
    private final LocationImageMapper locationImageMapper;

    public List<LocationInfo> findAtmLocations(Integer cityId) {
        List<Location> locations = locationRepository.findFilteredLocationsBy(cityId, STATUS_ACTIVE.getCode());
        validateAtLeastOneLocationExists(locations);
        List<LocationInfo> locationInfos = locationMapper.toLocationInfos(locations);
        addTransactionTypes(locationInfos);
        return locationInfos;
    }

    public List<LocationInfo> findAtmLocationsV2(Integer cityId) {
        List<LocationTransactionTypeView> locationTransactionTypeViews = locationTransactionTypeViewRepository.findFilteredLocationTransactionTypeViewsBy(cityId);
        validateAtLeastOneLocationTransactionTypeExists(locationTransactionTypeViews);
        return groupToLocationInfos(locationTransactionTypeViews);
    }

    private List<LocationInfo> groupToLocationInfos(List<LocationTransactionTypeView> locationTransactionTypeViews) {
        Map<Integer, LocationInfo> locationInfosByLocationId = new LinkedHashMap<>();
        for (LocationTransactionTypeView locationTransactionTypeView : locationTransactionTypeViews) {
            Integer locationId = locationTransactionTypeView.getId().getLocationId();
            LocationInfo locationInfo = locationInfosByLocationId.computeIfAbsent(locationId, id -> {
                LocationInfo newLocationInfo = locationTransactionTypeViewMapper.toLocationInfo(locationTransactionTypeView);
                newLocationInfo.setTransactionTypes(new ArrayList<>());
                return newLocationInfo;
            });
            TransactionTypeDto transactionTypeDto = locationTransactionTypeViewMapper.toTransactionTypeDto(locationTransactionTypeView);
            locationInfo.getTransactionTypes().add(transactionTypeDto);
        }
        return new ArrayList<>(locationInfosByLocationId.values());
    }

    private void addTransactionTypes(List<LocationInfo> locationInfos) {
        for (LocationInfo locationInfo : locationInfos) {
            List<TransactionTypeDto> transactionTypeDtos = createTransactionTypeDtos(locationInfo.getLocationId());
            locationInfo.setTransactionTypes(transactionTypeDtos);
        }
    }

    private List<TransactionTypeDto> createTransactionTypeDtos(Integer locationId) {
        Sort byNameDesc = Sort.by(Sort.Direction.DESC, "name");
        List<TransactionType> transactionTypes = transactionTypeRepository.findAll(byNameDesc);
        List<TransactionTypeDto> transactionTypeDtos = transactionTypeMapper.toTransactionTypeDtos(transactionTypes);

        for (TransactionTypeDto transactionTypeDto : transactionTypeDtos) {
            boolean locationTransactionTypeExists = locationTransactionTypeRepository.locationTransactionTypeExistsBy(locationId, transactionTypeDto.getTransactionTypeId());
            transactionTypeDto.setIsAvailable(locationTransactionTypeExists);
        }
        return transactionTypeDtos;
    }

    private static void validateAtLeastOneLocationExists(List<Location> locations) {
        if (locations.isEmpty()) {
            throw new DataNotFoundException(NO_LOCATION_FOUND.getMessage(), NO_LOCATION_FOUND.name());
        }
    }

    private static void validateAtLeastOneLocationTransactionTypeExists(List<LocationTransactionTypeView> locationTransactionTypeViews) {
        if (locationTransactionTypeViews.isEmpty()) {
            throw new DataNotFoundException(NO_LOCATION_FOUND.getMessage(), NO_LOCATION_FOUND.name());
        }
    }


    public void addLocation(LocationDto locationDto) {

        validateLocationNameIsAvailable(locationDto.getLocationName());
        Location location = createAndSaveLocation(locationDto);
        String imageDataAsString = locationDto.getImageData();
        handleCreateAndSaveLocationImage(locationDto, imageDataAsString, location);
        handleCreateAndSaveLocationTransactionTypes(locationDto, location);


    }

    private void handleCreateAndSaveLocationTransactionTypes(LocationDto locationDto, Location location) {
        for (TransactionTypeDto transactionTypeDto : locationDto.getTransactionTypes()) {
            if (transactionTypeDto.getIsAvailable()) {
                LocationTransactionType locationTransactionType = new LocationTransactionType();
                locationTransactionType.setLocation(location);

                TransactionType transactionType = transactionTypeRepository.findById(transactionTypeDto.getTransactionTypeId())
                        .orElseThrow(() -> new PrimaryKeyNotFoundException("transactionTypeId", transactionTypeDto.getTransactionTypeId()));
                locationTransactionType.setTransactionType(transactionType);
                locationTransactionTypeRepository.save(locationTransactionType);


            }
        }
    }

    private void handleCreateAndSaveLocationImage(LocationDto locationDto, String imageDataAsString, Location location) {
        if (!locationDto.getImageData().isEmpty()) {

            byte[] imageDataAsStringBytes = StringBytesConverter.stringToBytes(imageDataAsString);

            LocationImage locationImage = new LocationImage();
            locationImage.setLocation(location);
            locationImage.setData(imageDataAsStringBytes);
            locationImageRepository.save(locationImage);

        }
    }

    private @NonNull Location createAndSaveLocation(LocationDto locationDto) {
        Location location = createLocation(locationDto);
        locationRepository.save(location);
        return location;
    }

    private @NonNull Location createLocation(LocationDto locationDto) {
        City city = getValidCityBy(locationDto.getCityId());

        Location location = locationMapper.toLocation(locationDto);
        location.setCity(city);
        return location;
    }

    private @NonNull City getValidCityBy(Integer cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("cityId", cityId));
        return city;
    }

    private void validateLocationNameIsAvailable(String locationName) {
        boolean locationExists = locationRepository.locationExistsBy(locationName);
        if (locationExists) {
            throw new ForbiddenException(LOCATION_UNAVAILABLE.getMessage(), LOCATION_UNAVAILABLE.name());
        }
    }
}
