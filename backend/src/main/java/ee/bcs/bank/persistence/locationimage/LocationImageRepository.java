package ee.bcs.bank.persistence.locationimage;

import ee.bcs.bank.persistence.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationImageRepository extends JpaRepository<LocationImage, Integer> {
    @Query("select l from LocationImage l where l.location = :location")
    Optional<LocationImage> findByLocation( Location location);

}