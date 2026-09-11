package ee.bcs.bank.persistence.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {


    @Query("select l from Location l where ( :cityId = 0 or l.city.id = :cityid) and l.status = :status order by l.city.name, l.name")
    List<Location> findFilteredLocationsBy(Integer cityId, String status);



}