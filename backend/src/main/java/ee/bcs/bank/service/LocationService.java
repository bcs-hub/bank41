package ee.bcs.bank.service;


import ee.bcs.bank.persistence.location.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {


    private final CityRepository locationRepository;

    public void findAtmLocations(Integer cityId) {
        locationRepository
    }
}
