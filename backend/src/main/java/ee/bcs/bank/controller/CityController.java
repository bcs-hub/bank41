package ee.bcs.bank.controller;

import ee.bcs.bank.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/api/cities")
    public void findCities(){
        cityService.findCities();



    }

}
