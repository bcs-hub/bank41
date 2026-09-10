package ee.bcs.bank.service;

import ee.bcs.bank.persistence.city.City;
import ee.bcs.bank.persistence.city.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityService {


    private final CityRepository cityRepository;

    public void findCities() {
        List<City> cities = cityRepository.findAll();
    return cities;
    }
}
