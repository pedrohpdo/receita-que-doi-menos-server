package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserResponseDTO(
        String id,
        String name,
        String email
) {}
