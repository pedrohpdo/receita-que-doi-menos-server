package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDTO(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken
) {
}
