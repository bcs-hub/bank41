package ee.bcs.bank.controller.city.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto implements Serializable {
    private Integer cityId;
    private String cityName;
}