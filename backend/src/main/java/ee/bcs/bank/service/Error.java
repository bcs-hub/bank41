package ee.bcs.bank.infrastructure.error;

import lombok.Getter;

@Getter
public enum Error {

    INCORRECT_CREDENTIALS("Vale kasutajanimi või parool", "INCORRECT_CREDENTIALS");

    private final String message;
    private final String errorCode;

    Error(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
