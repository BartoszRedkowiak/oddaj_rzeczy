package pl.coderslab.charity.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.user.User;

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
        Token existingToken = tokenRepository.findByTokenType(type);
        if (existingToken != null){
            tokenRepository.delete(existingToken);
        }
        //New token generation
        Token token = new Token();
        token.setUser(user);
        token.setTokenType(type);
        token.setToken(UUID.randomUUID().toString());

        create(token);
        return token;
    }

    public Token findOneByToken(String token){
         return  tokenRepository.findByToken(token);
    }

    public Token findOneByTokenAndType(String token, Byte type){
         return  tokenRepository.findByTokenAndTokenType(token, type);
    }

    public void deleteToken (String token){
        tokenRepository.removeByToken(token);
    }

    public void delete(Long id){
        tokenRepository.deleteById(id);
    }

    public void create(Token token){ tokenRepository.save(token); }


}
