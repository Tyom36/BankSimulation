package rogue.tyom.sub.securityapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.services.BankAccountDetailsService;
import rogue.tyom.sub.securityapp.util.PersonException;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private final BankAccountDetailsService bankAccountDetailsService;


    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("bankSimApi")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndGetUsername(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("bankSimApi")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

    public String getUsernameFromToken(String authorizationHeader) {
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        String username = validateTokenAndGetUsername(token);
        return username;
    }

    public void checkForPersonalData(int personId, String authorizationHeader) {
        Optional<BankAccount> bankAccount = bankAccountDetailsService.findByUsername(getUsernameFromToken(authorizationHeader));

        if (bankAccount.isEmpty() || bankAccount.get().getId() != personId) {
            throw new PersonException("Работать можно только со своими данными!");
        }
    }
}
