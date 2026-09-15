package ee.bcs.bank.persistence.locationtransactiontypeview;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

/**
 * Loetavuseks mõeldud entiteet, mis põhineb andmebaasi vaatel {@code bank.location_transaction_type_view}.
 * Vaade koondab city, location ja transaction_type andmed üheks lameda struktuuriga reaks
 * (õppise eesmärgil, vt {@code docs/database/2_create.sql}).
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "location_transaction_type_view", schema = "bank")
public class LocationTransactionTypeView {

    @EmbeddedId
    private LocationTransactionTypeViewId id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "location_status", nullable = false, length = Integer.MAX_VALUE)
    private String locationStatus;

    @Column(name = "lng", precision = 10, scale = 7)
    private BigDecimal lng;

    @Column(name = "lat", precision = 10, scale = 7)
    private BigDecimal lat;

    @Column(name = "transaction_type_name", nullable = false)
    private String transactionTypeName;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

}
