package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.TokenException;
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

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.access.duration}")
    private long accessTokenExpiration;

    @Value("${api.security.token.refresh.duration}")
    private long refreshTokenExpiration;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withClaim("user_id", user.getId())
                    .withClaim("user_name", user.getName())
                    .withSubject(user.getEmail())
                    .withExpiresAt(getDuration(this.accessTokenExpiration))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new TokenException(e.getMessage());
        }
    }

    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withClaim("user_id", user.getId())
                    .withClaim("user_name", user.getName())
                    .withSubject(user.getEmail())
                    .withExpiresAt(getDuration(this.refreshTokenExpiration))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new TokenException(e.getMessage());
        }
    }

    public String validateToken(String token) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(this.secret);
                return JWT.require(algorithm)
                        .withIssuer(issuer)
                        .build()
                        .verify(token)
                        .getSubject();

            } catch (JWTVerificationException e){
                throw new TokenException(e.getMessage());
            }
    }


    private Instant getDuration(long duration){
        return LocalDateTime.now().plusHours(duration).toInstant(ZoneOffset.of("-03:00"));
    }
}
