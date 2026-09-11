package ee.bcs.bank.controller.location;

import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;


    @GetMapping("/api/atm/locations")
    @Operation(
            summary = "Tagastab pangaautomaatide asukohtade infot",
            description = "Kui cityId on 0, siis tagastatakse kõik asukohad"
    )
    public List<LocationInfo> findAtmLocations(@RequestParam Integer cityId) {
        List<LocationInfo> locationInfos = locationService.findAtmLocations(cityId);
        return locationInfos;

    }
}
