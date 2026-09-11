package ee.bcs.bank.persistence.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query("select l from Location l where l.city.id = :cityId and l.status = :status order by l.city.name, l.name")
    List<Location> findLocationsBy(Integer cityId, String status);

}