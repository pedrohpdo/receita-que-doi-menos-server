package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.create()
                    .withIssuer("receita-develop")
                    .withSubject(user.getEmail())
                    .withExpiresAt(getDuration())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw  new RuntimeException("Error Generating Token", e.getCause());
        }
    }

    public String validateToken(String token) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(this.secret);
                return JWT.require(algorithm)
                        .withIssuer("receita-develop")
                        .build()
                        .verify(token)
                        .getSubject();

            } catch (JWTVerificationException e){
                return "";
            }
    }

    private Instant getDuration(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
