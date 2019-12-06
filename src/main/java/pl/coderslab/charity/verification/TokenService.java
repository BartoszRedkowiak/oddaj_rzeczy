package pl.coderslab.charity.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.user.User;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;
    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token generateToken(User user, Byte type){
        //Test for same type token existence
        Token existingToken = tokenRepository.findByCodeType(type);
        if (existingToken != null){
            tokenRepository.delete(existingToken);
        }
        //New token generation
        Token token = new Token();
        token.setUser(user);
        token.setCodeType(type);
        token.setToken(UUID.randomUUID().toString());

        create(token);
        return token;
    }

    public Token findOneByToken(String token){
         return  tokenRepository.findByToken(token);
    }

    public Token findOneByTokenAndType(String token, Byte type){
         return  tokenRepository.findByTokenAndCodeType(token, type);
    }

    public void deleteToken (String token){
        tokenRepository.removeByToken(token);
    }

    public void create(Token token){ tokenRepository.save(token); }


}
