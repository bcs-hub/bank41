package ee.bcs.bank.service;

import ee.bcs.bank.controller.LoginRequest;
import ee.bcs.bank.persistence.user.User;
import ee.bcs.bank.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public void loginUser(LoginRequest loginRequest) {
      User optionalUser = userRepository.findUserBy(loginRequest.getUsername(), loginRequest.getPassword(), "A");
        System.out.println("optionalUser" + optionalUser);
    }
}
