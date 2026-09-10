package ee.bcs.bank.service;

import ee.bcs.bank.Error;
import ee.bcs.bank.Status;
import ee.bcs.bank.controller.LoginRequest;
import ee.bcs.bank.controller.LoginResponse;
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

        User user = userRepository.findUserBy(loginRequest.getUsername(), loginRequest.getPassword(), STATUS_ACTIVE.getCode())
                .orElseThrow(() -> new ForbiddenException(INCORRECT_CREDENTIALS.getMessage(), INCORRECT_CREDENTIALS.name()));
        LoginResponse loginResponse = userMapper.toLoginResponse(user);

        return loginResponse;
    }

    }
