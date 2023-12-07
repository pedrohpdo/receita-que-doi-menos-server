package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Informações referentes a um usuário criado ou atualizados requeridos dentro do sistema
 *
 * @param name     String referente ao nome do usuário
 * @param email    String referente ao email do usuário
 * @param password String referente a senha do usuário
 * @author Pedro Henrique Pereira de Oliveira
 */
public record UserRequestDTO(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "email is required")
        @Email
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 8, max = 12, message = "password must have size 8 - 12")
        String password,

        String profilePhoto
) {
}
