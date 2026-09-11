package ee.bcs.bank.persistence.locationtransactiontype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationTransactionTypeRepository extends JpaRepository<LocationTransactionType, Integer> {
    @Query("""
            select (count(l) > 0) from LocationTransactionType l
            where l.location.id = :locationId and l.transactionType.id = :transactionTypeId""")
    boolean locationTransactionTypeExistsBy(Integer locationId, Integer transactionTypeId);


}