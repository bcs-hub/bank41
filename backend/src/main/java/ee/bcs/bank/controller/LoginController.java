package ee.bcs.bank.controller;

import ee.bcs.bank.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/api/login")
    @Operation(summary = "Sisse logimine. Tagastab userId ja roleName")
    @ApiResponses(
            @ApiResponse(
                    responseCode = "403",
                    description = "Ebaõnnestunud sisselogimisel kuvatakse -> 'message:' Vale kasutajanimi või parool; 'errorCode': INCORRECT_CREDENTIALS"
            )
    )
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = loginService.loginUser(loginRequest);
        return loginResponse;
    }

}
