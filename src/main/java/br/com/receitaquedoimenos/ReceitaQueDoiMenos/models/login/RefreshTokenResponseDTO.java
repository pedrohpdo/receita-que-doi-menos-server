package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Novo token de acesso solicitado pelo usuário.
 *
 * @param accessToken Novo token de acesso
 */
public record RefreshTokenResponseDTO(
        @JsonProperty("access-token") String accessToken
) {
}
