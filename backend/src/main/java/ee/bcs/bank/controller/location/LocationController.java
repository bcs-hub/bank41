package ee.bcs.bank.controller.location;

import ee.bcs.bank.controller.location.dto.LocationInfo;
import ee.bcs.bank.infrastructure.error.ApiError;
import ee.bcs.bank.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Kui ühtegi asukohta ei leita, siis 'message': Ei leitud ühtegi pangaautomaati, 'errorCode:' NO_LOCATION_FOUND",
                    content = @Content(schema = @Schema(implementation = ApiError.class))

            )
    }
    )


    public List<LocationInfo> findAtmLocations(@RequestParam Integer cityId) {
        List<LocationInfo> locationInfos = locationService.findAtmLocations(cityId);

        return locationInfos;

    }
}
