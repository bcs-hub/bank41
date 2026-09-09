package ee.bcs.bank.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

//    @Query("select u from User u where u.username = :username and u.password = :password and u.status = :status")
//    User findUserByUsernameAndPasswordAndStatus(@Param("username") String username, @Param("password") String password, @Param("status") String status);

    @Query("select u from User u where u.username = :username and u.password = :password and u.status = :status")
    Optional<User> findUser(String username, String password, String status); //    User findByUsernameAndPasswordAndStatus(String username, String password, String status);


}