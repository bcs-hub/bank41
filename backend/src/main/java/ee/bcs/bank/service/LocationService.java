package ee.bcs.bank.service;

import ee.bcs.bank.persistence.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public void findAtmLocations(Integer cityId) {

    }
}
