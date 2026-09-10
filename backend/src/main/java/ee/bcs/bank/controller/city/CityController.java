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

    @GetMapping("/api/cities")
    @Operation(summary = "Leiab süsteemist kõik linnad")
    public List<CityDto> findCities(){
        List<CityDto> cityDtos = cityService.findCities();
        return cityDtos;
    }

}
