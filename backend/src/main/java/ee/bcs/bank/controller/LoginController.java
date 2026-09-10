package ee.bcs.bank.controller;

import ee.bcs.bank.persistence.user.User;
import ee.bcs.bank.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

//    public LoginController(LoginService loginService) {
//        this.loginService = loginService;
//    }

    @PostMapping("/api/login")
    @Operation(summary = "Sisse logimine. Tagastab userId ja roleName")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.loginUser(loginRequest);
        return loginResponse;
    }


}
