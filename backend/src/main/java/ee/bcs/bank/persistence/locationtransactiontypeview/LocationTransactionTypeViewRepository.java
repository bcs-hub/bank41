package ee.bcs.bank.persistence.locationtransactiontypeview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationTransactionTypeViewRepository extends JpaRepository<LocationTransactionTypeView, LocationTransactionTypeViewId> {

    @Query("""
            select v from LocationTransactionTypeView v
            where (:cityId = 0 or v.id.cityId = :cityId)
            order by v.cityName, v.locationName, v.transactionTypeName""")
    List<LocationTransactionTypeView> findFilteredLocationTransactionTypeViewsBy(Integer cityId);

}
