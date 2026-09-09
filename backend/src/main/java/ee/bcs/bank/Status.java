package ee.bcs.bank;


import lombok.Getter;

@Getter
public enum Status {
    STATUS_ACTIVE ("A");
    STATUS_DELETED("D"),
}

public final String