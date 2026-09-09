package ee.bcs.bank.controller;

import ee.bcs.bank.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/login")
    public void loginUser(@RequestBody LoginRequest loginRequest) {
        loginService.loginUser(loginRequest);
    }

}
