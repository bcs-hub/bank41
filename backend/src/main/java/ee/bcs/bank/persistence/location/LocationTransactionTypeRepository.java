package ee.bcs.bank.persistence.location;

import ee.bcs.bank.persistence.LocationTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationTransactionTypeRepository extends JpaRepository<LocationTransactionType, Integer> {

    @Query("""
            select new ee.bcs.bank.persistence.location.LocationTransactionTypeDto(tt.id, tt.name, true)
            from LocationTransactionType ltt
            join ltt.transactionType tt
            where ltt.location.id = :locationId
            """)
    List<LocationTransactionTypeDto> findLocationTransactionTypesBy(@Param("locationId") Integer locationId);
}
