package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        @Min(8)
        String password
){}
