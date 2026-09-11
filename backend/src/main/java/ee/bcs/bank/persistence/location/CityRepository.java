package ee.bcs.bank.persistence.location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<Location, Integer> {
}