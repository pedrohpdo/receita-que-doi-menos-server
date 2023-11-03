package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenResponseDTO(
        @JsonProperty("access-token") String accessToken
) {
}
