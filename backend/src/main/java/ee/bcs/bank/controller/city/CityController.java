package ee.bcs.bank.controller.city;


import ee.bcs.bank.controller.city.dto.CityDto;
import ee.bcs.bank.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CityController {


    private final CityService cityService;

@Operation(summary = "Leiab süsteemist kõik linnad.")
    @GetMapping(value = "/api/cities")
    public List<CityDto> findCities() {
        List<CityDto> cityDtos = cityService.findCities();
        return cityDtos;
    }
}
