package ee.bcs.bank.service;

import ee.bcs.bank.persistence.city.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {


    private final CityRepository cityRepository;

    public void findCities() {
        cityRepository

    }
}
