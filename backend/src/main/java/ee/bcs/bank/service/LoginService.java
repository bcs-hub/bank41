package ee.bcs.bank.service;

import ee.bcs.bank.controller.LoginRequest;
import ee.bcs.bank.persistence.user.User;
import ee.bcs.bank.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public void loginUser(LoginRequest loginRequest) {

        Optional<User> optionalUser = userRepository.findUserBy(loginRequest.getUsername(), loginRequest.getPassword(), "A");

    }

}
