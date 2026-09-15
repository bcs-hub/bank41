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
    @Operation(summary ="Uue pangaautomaadi lisamine.",
        description = """
                Pildi lisamine pole kohustuslik; pildi puudumisel saadetakse imageData väärtuseks tühi string. 
                transactionTypeName infot koodis ei kasutata""")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(
                    responseCode = "403",
                    description = "Selle nimega asukoht on juba süsteemis olemas, 'errorCode': 'LOCATION_UNAVAILABLE'",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )

    })
    public void addLocation(@RequestBody LocationDto locationDto) {
        locationService.addLocation(locationDto);
    }

    @GetMapping("/atm/locations")
    @Operation(
            summary = "Tagastab pangaautomaatide asukohtade infot",
            description = "Kui cityId on 0, siis tagastatakse kõik asukohad"
    )
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Kui ühtegi pangaautomaati ei leita, siis 'message': 'Ei leitud ühtegi pangaautomaati', 'errorCode': 'NO_LOCATION_FOUND'",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    )}
    )
    public List<LocationInfo> findAtmLocations(@RequestParam Integer cityId) {
        List<LocationInfo> locationInfos = locationService.findAtmLocations(cityId);
        return locationInfos;
    }



}
