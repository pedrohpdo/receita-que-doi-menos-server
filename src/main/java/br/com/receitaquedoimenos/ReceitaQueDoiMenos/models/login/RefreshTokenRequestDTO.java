package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenRequestDTO(
        @JsonProperty(value = "refresh-token") String refreshToken
) {
}
