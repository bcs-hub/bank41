package ee.bcs.bank.persistence.city;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;


@Value
public class CityDto implements Serializable {
    Integer cityId;
    @NotNull
    @Size(max = 255)
    String cityName;
}