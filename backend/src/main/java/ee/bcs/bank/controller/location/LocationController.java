package ee.bcs.bank.controller.location;

import ee.bcs.bank.controller.location.dto.LocationDto;
import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.infrastructure.error.ApiError;
import ee.bcs.bank.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/atm/location")
    @Operation(summary = "Uue pangaautomaadi lisamine.",
            description = """
                    Pildi lisamine pole kohustuslik.
                    Pildi puudumisel saadetakse imageData väärtuseks tühi string.
                    transactionTypeName infot koodis ei kasutata."""
    )
    public void addLocation(@RequestBody LocationDto locationDto) {
        locationService.addLocation(locationDto);
    }

    @GetMapping("/atm/locations")
    @Operation(
            summary = "Tagastab pangaautomaatide asukohtade infot",
            description = "Kui cityId on 0, siis tagastatakse kõik asukohad"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Kui ühtegi askohta ei leita, siis 'message': Ei leitud ühtegi pangaautomaati, 'errorCode:' NO_LOCATION_FOUND",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public List<LocationInfo> findAtmLocations(@RequestParam Integer cityId) {
        List<LocationInfo> locationInfos = locationService.findAtmLocations(cityId);
        return locationInfos;
    }

    @GetMapping("/v2/atm/locations")
    @Operation(
            summary = "Tagastab pangaautomaatide asukohtade infot (v2, õppise eesmärgil)",
            description = "Kui cityId on 0, siis tagastatakse kõik asukohad. Andmed pärineb database view'st " +
                    "bank.location_transaction_type_view ning tulemus on lameda struktuuriga (üks rida iga " +
                    "asukoha-tehingutüübi kombinatsiooni kohta)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Kui ühtegi askohta ei leita, siis 'message': Ei leitud ühtegi pangaautomaati, 'errorCode:' NO_LOCATION_FOUND",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public List<LocationInfo> findAtmLocationsV2(@RequestParam Integer cityId) {
        List<LocationInfo> locationInfos = locationService.findAtmLocationsV2(cityId);
        return locationInfos;
    }


}
