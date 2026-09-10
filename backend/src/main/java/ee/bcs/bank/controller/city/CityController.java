package ee.bcs.bank.controller.city;

import ee.bcs.bank.controller.city.dto.CityDto;
import ee.bcs.bank.persistence.city.City;
import ee.bcs.bank.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/api/cities")
    public List<CityDto> findCities() {
        List<CityDto> cities = cityService.findCities();
        return cities;
    }






}
