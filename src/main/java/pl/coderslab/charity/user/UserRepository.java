package pl.coderslab.charity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Integer countAllByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findBySpecificRole(@Param("roleName") String roleName);

    @Query("SELECT u FROM User u JOIN u.tokens t WHERE t.token = :token")
    User findByToken(@Param("token") String token);


}
