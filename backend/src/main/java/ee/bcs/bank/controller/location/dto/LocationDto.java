package ee.bcs.bank.controller.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link ee.bcs.bank.persistence.location.Location}
 */
// Sisendobjekt POST /api/atm/location jaoks — see, mida frontend uue asukoha
// lisamisel serverile saadab. "status" ja "id" siin teadlikult puuduvad,
// kuna need määratakse serveris (vt LocationMapper.toLocation()).
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto implements Serializable {
    private Integer cityId;
    private String locationName;
    private Integer numberOfAtms;
    // Pilt on valikuline — kui kasutaja pilti ei lisa, saadetakse tühi string, mitte null
    private String imageData;
    private BigDecimal lng;
    private BigDecimal lat;
    // transactionTypeName infot mapperis ei kasutata, oluline on ainult transactionTypeId + isAvailable
    private List<TransactionTypeDto> transactionTypes;
}