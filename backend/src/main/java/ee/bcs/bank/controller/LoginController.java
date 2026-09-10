package ee.bcs.bank.controller;

import ee.bcs.bank.infrastructure.error.ApiError;
import ee.bcs.bank.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping(value="/api/login")
    @Operation(summary = "Sisse logimine. Tagastab userId ja roleName")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "200", description = "ok"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Ebaõnnestunud sisselogimisel kuvatakse -> 'message': Vale kasutajanimi või parool; 'errorCode': INCORRECT_CREDENTIALS",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    )}
    )
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return loginService.loginUser(loginRequest);
    }
}
