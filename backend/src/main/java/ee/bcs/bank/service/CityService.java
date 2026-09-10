package ee.bcs.bank.service;

import ee.bcs.bank.controller.city.dto.CityDto;
import ee.bcs.bank.persistence.city.City;
import ee.bcs.bank.persistence.city.CityMapper;
import ee.bcs.bank.persistence.city.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public List<CityDto> findCities() {
        List<City> cities = cityRepository.findAll();

//        List<CityDto> citiesDto = new ArrayList<>();
        List<CityDto> citiesDto = cityMapper.toCityDtos(cities);


//        for (City city : cities) {
//           citiesDto.add(cityMapper.toCityDto(city));
//
//        }

        return citiesDto;

    }
}
