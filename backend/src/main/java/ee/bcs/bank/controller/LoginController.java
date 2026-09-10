package ee.bcs.bank.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @PostMapping("/api/login")
    public void loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("kasutajanimi: " + loginRequest.getUsername());
        System.out.println("parool: " + loginRequest.getPassword());
    }
}
