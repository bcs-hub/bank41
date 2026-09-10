package ee.bcs.bank.service;


import ee.bcs.bank.controller.login.dto.LoginRequest;
import ee.bcs.bank.controller.login.dto.LoginResponse;
import ee.bcs.bank.infrastructure.exception.ForbiddenException;
import ee.bcs.bank.persistence.user.User;
import ee.bcs.bank.persistence.user.UserMapper;
import ee.bcs.bank.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static ee.bcs.bank.Error.INCORRECT_CREDENTIALS;
import static ee.bcs.bank.Status.STATUS_ACTIVE;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public LoginResponse loginUser(LoginRequest loginRequest) {
//        throw new ForbiddenException(INCORRECT_CREDENTIALS.getMessage(), INCORRECT_CREDENTIALS.name());

        User user = userRepository.findUser(loginRequest.getUsername(), loginRequest.getPassword(), STATUS_ACTIVE.getCode())
                .orElseThrow(() -> new ForbiddenException(INCORRECT_CREDENTIALS.getMessage(), INCORRECT_CREDENTIALS.name()));

        return userMapper.toLoginResponse(user);

//        User user = userRepository.findUserByUsernameAndPasswordAndStatus(loginRequest.getUsername(), loginRequest.getPassword(), "A");
//        Optional<User> optionalUser = userRepository.findUser(loginRequest.getUsername(), loginRequest.getPassword(), Status.STATUS_ACTIVE.getCode());
//
//        if (optionalUser.isPresent()) {
//
//            User user = optionalUser.get();
//            return userMapper.toLoginResponse(user);
//
//        } else {
//            throw new ForbiddenException(INCORRECT_CREDENTIALS.getMessage(), INCORRECT_CREDENTIALS.name());
//        }

    }

}
