package ee.bcs.bank;

import lombok.Getter;

@Getter
public enum Error {
    INCORRECT_CREDENTIALS("Vale kasutajanimi või parool!");

    private final String message;

    Error(String message) {
        this.message = message;
    }
}
