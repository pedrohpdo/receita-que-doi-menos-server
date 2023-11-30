package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.TokenException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * A classe TokenService fornece métodos para geração, validação e manipulação de tokens de acesso e refresh.
 * Utiliza a biblioteca Auth0 JWT para criar e verificar tokens com base em informações do usuário.
 * As configurações, como segredo, emissário (issuer) e duração dos tokens, são obtidas a partir das propriedades
 * configuradas no arquivo de propriedades da aplicação.
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @version 1.0
 * @since 2023.2
 */
@Service
@Slf4j(topic = "TOKEN_SERVICE")
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.access.duration}")
    private long accessTokenExpiration;

    @Value("${api.security.token.refresh.duration}")
    private long refreshTokenExpiration;

    /**
     * Gera um token de acesso com base nas informações do usuário.
     *
     * @param user O usuário para o qual o token está sendo gerado.
     * @return O token de acesso gerado.
     * @throws TokenException Se ocorrer um erro durante a criação do token.
     */
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

    /**
     * Gera um token de refresh com base nas informações do usuário.
     *
     * @param user O usuário para o qual o token de refresh está sendo gerado.
     * @return O token de refresh gerado.
     * @throws TokenException Se ocorrer um erro durante a criação do token de refresh.
     */
    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withClaim("user_id", user.getId())
                    .withSubject(user.getEmail())
                    .withExpiresAt(getDuration(this.refreshTokenExpiration))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new TokenException(e.getMessage());
        }
    }

    /**
     * Valida um token de acesso, verificando sua integridade e prazo de validade.
     *
     * @param token O token de acesso a ser validado.
     * @return O e-mail do usuário associado ao token.
     * @throws TokenException Se ocorrer um erro durante a validação do token.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            throw new TokenException(e.getMessage());
        }
    }

    /**
     * Obtém a instância de Instant que representa o momento de expiração com base na duração fornecida.
     *
     * @param duration A duração em segundos até a expiração.
     * @return A instância de Instant representando o momento de expiração.
     */
    private Instant getDuration(long duration) {
        return LocalDateTime.now().plusHours(duration).toInstant(ZoneOffset.of("-03:00"));
    }
}
