package ee.bcs.bank.persistence.user;

import ee.bcs.bank.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsernameAndPasswordAndStatus(String username, String password, String status);

    List<User> Status(String status);
}