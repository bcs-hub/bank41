package ee.bcs.bank.service;


import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.controller.location.dto.TransactionTypeDto;
import ee.bcs.bank.infrastructure.exception.DataNotFoundException;
import ee.bcs.bank.persistence.location.Location;
import ee.bcs.bank.persistence.location.LocationMapper;
import ee.bcs.bank.persistence.location.LocationRepository;
import ee.bcs.bank.persistence.locationtransactiontype.LocationTransactionTypeRepository;
import ee.bcs.bank.persistence.transactiontype.TransactionType;
import ee.bcs.bank.persistence.transactiontype.TransactionTypeMapper;
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
    private final TransactionTypeMapper transactionTypeMapper;
    private final LocationTransactionTypeRepository locationTransactionTypeRepository;

    public List<LocationInfo> findAtmLocations(Integer cityId) {
        List<Location> locations = locationRepository.findFilteredLocationsBy(cityId, STATUS_ACTIVE.getCode());
        validateAtLestOneLocationExists(locations);
        List<LocationInfo> locationInfos = locationMapper.toLocationInfos(locations);
        addTransactionTypes(locationInfos);
        return locationInfos;
    }

    private void addTransactionTypes(List<LocationInfo> locationInfos) {
        for (LocationInfo locationinfo : locationInfos) {
            List<TransactionTypeDto> transactionTypeDtos = createTransactionTypeDtos(locationinfo);
            locationinfo.setTransactionTypes(transactionTypeDtos);
        }
    }

    private List<TransactionTypeDto> createTransactionTypeDtos(LocationInfo locationinfo) {
        Sort byNameDesc = Sort.by(Sort.Direction.DESC, "name");
        List<TransactionType> transactionTypes = transactionTypeRepository.findAll(byNameDesc);
        List<TransactionTypeDto> transactionTypeDtos = transactionTypeMapper.toTransactionTypeDtos(transactionTypes);

        for (TransactionTypeDto transactionTypeDto : transactionTypeDtos) {
            boolean locationTransactionTypeExists = locationTransactionTypeRepository.locationTransactionTypeExistsBy(locationinfo.getLocationId(), transactionTypeDto.getTransactionTypeId());
            transactionTypeDto.setIsAvailable(locationTransactionTypeExists);
        }
        return transactionTypeDtos;
    }

    private static void validateAtLestOneLocationExists(List<Location> locations) {
        if (locations.isEmpty()) {
            throw new DataNotFoundException(NO_LOCATION_FOUND.getMessage(), NO_LOCATION_FOUND.name());
        }
    }
}
