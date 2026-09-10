package ee.bcs.bank.service;

import ee.bcs.bank.controller.city.dto.CityDto;
import ee.bcs.bank.persistence.city.City;
import ee.bcs.bank.persistence.city.CityMapper;
import ee.bcs.bank.persistence.city.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CityService {


    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public List<CityDto> findCities() {
        List<City> cities = cityRepository.findAll();


        List<CityDto> cityDtos = new ArrayList<>();
        for (City city : cities) {
            CityDto cityDto = cityMapper.toCityDto(city);
            cityDtos.add(cityDto)
        }

    return cityDtos;
    }
}
