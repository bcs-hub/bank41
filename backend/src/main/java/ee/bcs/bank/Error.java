package ee.bcs.bank;

import lombok.Getter;

@Getter
public enum Error {
    INCORRECT_CREDENTIALS("Vale kasutajanimi või parool"),
    NO_LOCATION_FOUND("Ei leitud ühtegi pangaautomaati"),
    LOCATION_UNAVAILABLE("Sellise nimega pangaautomaadi asukoht on juba süsteemis olemas");


    private final String message;

    Error(String message) {
        this.message = message;
    }
}
