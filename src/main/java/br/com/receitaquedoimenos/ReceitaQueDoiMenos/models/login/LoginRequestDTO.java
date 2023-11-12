package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Dados referentes ao Login do Usuário na aplicação
 *
 * @param email    String referente ao email do usuário
 * @param password String referente a senha do usuário
 */
public record LoginRequestDTO(
        @NotBlank(message = "email is required")
        @Email
        String email,

        @NotBlank
        @Size(min = 8, max = 12, message = "Senha deve ter pelo menos 8 caracteres e no máximo 12")
        String password) {
}
