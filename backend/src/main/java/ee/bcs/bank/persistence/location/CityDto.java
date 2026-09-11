package ee.bcs.bank.persistence.location;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ee.bcs.bank.persistence.city.City}
 */
@Value
public class CityDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 255)
    String name;
}