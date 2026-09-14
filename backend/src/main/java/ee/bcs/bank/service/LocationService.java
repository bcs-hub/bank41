package ee.bcs.bank.service;

import ee.bcs.bank.controller.location.dto.LocationDto;
import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.controller.location.dto.TransactionTypeDto;
import ee.bcs.bank.infrastructure.exception.DataNotFoundException;
import ee.bcs.bank.persistence.location.Location;
import ee.bcs.bank.persistence.location.LocationMapper;
import ee.bcs.bank.persistence.location.LocationRepository;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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


    // TODO see meetod on hetkel pooleli (õpetaja "wip" commit):
    //  1) location.setCity(...) pole veel pandud, seega city_id jääks NULL-iks
    //  2) locationRepository.save(location) puudub, seega midagi ei salvestata andmebaasi
    //  3) transactionTypes DTO-st ei tehta veel location_transaction_type kirjeid
    //  System.out.println() on debug-jääk, mille eesmärk oli lihtsalt kontrollida, et
    //  mapper õieti töötab (pane breakpoint siia rea peale, et "location" objekti näha).
    public void addLocation(LocationDto locationDto) {
        Location location = locationMapper.toLocation(locationDto);
        System.out.println();
    }
}
