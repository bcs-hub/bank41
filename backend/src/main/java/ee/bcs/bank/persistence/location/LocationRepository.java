package ee.bcs.bank.persistence.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {


    @Query("select l from Location l where l.city.id = :id and l.status = :status order by l.city.name, l.name")
    List<Location> findByCity_IdAndStatusOrderByCity_NameAscNameAsc(@Param("id") Integer id, @Param("status") String status);
}