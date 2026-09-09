package ee.bcs.bank.service;


import ee.bcs.bank.Status;
import ee.bcs.bank.controller.LoginRequest;
import ee.bcs.bank.controller.LoginResponse;
import ee.bcs.bank.persistence.user.User;
import ee.bcs.bank.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {



    private final UserRepository userRepository;

    public LoginResponse loginUser(LoginRequest loginRequest) {
//        User user = userRepository.findUserByUsernameAndPasswordAndStatus(loginRequest.getUsername(), loginRequest.getPassword(), "A");
        Optional<User> optionalUser = userRepository.findUser(loginRequest.getUsername(), loginRequest.getPassword(), Status.STATUS_ACTIVE.getCode());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            LoginResponse loginResponse = new LoginResponse(user.getId(), user.getRole().getName());

            return loginResponse;
        }

        return null;

    }

}
