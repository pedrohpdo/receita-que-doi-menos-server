package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Resposta ao Login do usuário
 *
 * @param accessToken  String correspondente Token de acesso
 * @param refreshToken String correspondente ao refresh token
 */
public record LoginResponseDTO(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken
) {
}
