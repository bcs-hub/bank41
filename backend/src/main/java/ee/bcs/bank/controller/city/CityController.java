package ee.bcs.bank.controller.city;

import ee.bcs.bank.persistence.city.City;
import ee.bcs.bank.persistence.city.CityDto;
import ee.bcs.bank.persistence.city.CityMapper;
import ee.bcs.bank.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //Spring saab aru, et see on Controller Api jaoks
@RequiredArgsConstructor  // Loob automaatselt konstruktori kõikide final väljade jaoks
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    //meetod
    @GetMapping("/api/cities")
    @Operation(summary = "Leiab susteemist koik linnad")
    public List<CityDto> findCities() {
        List<CityDto> cityDtos = cityService.findCities();
        return cityDtos;
    }

}
