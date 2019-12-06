package pl.coderslab.charity.verification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);

    Token findByTokenAndCodeType(String token, Byte type);

    void removeByToken(String token);

    Token findByCodeType(Byte type);

}
