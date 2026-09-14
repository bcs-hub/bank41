package ee.bcs.bank.persistence.locationtransactiontypeview;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Vaate {@code bank.location_transaction_type_view} komposiitvõti: üks rida on üheselt määratud
 * location_id + transaction_type_id kombinatsiooniga (city_id tuleneb location_id-st).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class LocationTransactionTypeViewId implements Serializable {

    @Column(name = "city_id", nullable = false)
    private Integer cityId;

    @Column(name = "location_id", nullable = false)
    private Integer locationId;

    @Column(name = "transaction_type_id", nullable = false)
    private Integer transactionTypeId;

}
