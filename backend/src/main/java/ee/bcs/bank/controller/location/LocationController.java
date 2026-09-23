package ee.bcs.bank.controller.location;

import ee.bcs.bank.controller.location.dto.AtmLocationDetailDto;
import ee.bcs.bank.controller.location.dto.LocationDto;
import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.infrastructure.error.ApiError;
import ee.bcs.bank.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "'message': Sellise nimega pangaautomaadi asukoht on juba süsteemis olemas, 'errorCode:' LOCATION_UNAVAILABLE",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "'message': Ei leidnud primary keyd 'x' väärtusega 'y', 'errorCode:' PRIMARY_KEY_NOT_FOUND",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public void addLocation(@RequestBody @Valid LocationDto locationDto) {
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

    @GetMapping("/atm/locations/details")
    @Operation(
            summary = "Tagastab pangaautomaatide asukohtade detailinfo koos piltidega",
            description = "Kui cityId on 0, siis tagastatakse kõik asukohad. Erinevalt /atm/locations " +
                    "endpointist sisaldab vastus ka cityId, numberOfAtms ja imageData välju."
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
    public List<AtmLocationDetailDto> findAtmLocationDetails(@RequestParam Integer cityId) {
        List<AtmLocationDetailDto> atmLocationDetailDtos = locationService.findAtmLocationDetails(cityId);
        return atmLocationDetailDtos;
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

    @GetMapping("/atm/location/{locationId}")
    @Operation(
            summary = "Tagastab ühe pangaautomaadi asukoha detailandmed",
            description = "Kasutatakse asukoha muutmisvormi eeltäitmiseks. transactionTypes massiiv sisaldab " +
                    "alati kõiki süsteemis defineeritud tehingutüüpe, igaühe isAvailable väärtus kajastab, " +
                    "kas see on selles asukohas hetkel saadaval."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "'message': Ei leidnud primary keyd 'locationId' väärtusega: 'x', 'errorCode:' PRIMARY_KEY_NOT_FOUND",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ootamatu serveripoolne viga",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public LocationDto getLocation(@PathVariable Integer locationId) {
        LocationDto locationDto = locationService.getLocation(locationId);
        return locationDto;
    }

    @PutMapping("/atm/location/{locationId}")
    public void updateLocation(@PathVariable Integer locationId, @RequestBody LocationDto locationDto) {
        locationService.updateLocation(locationId, locationDto);
    }

}
