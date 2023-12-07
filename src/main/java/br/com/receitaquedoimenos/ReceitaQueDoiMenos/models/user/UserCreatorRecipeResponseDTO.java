package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

/**
 * Informações referentes ao criador de cada refeição ou drink consultado
 *
 * @param id    String correspondente ao ID do criador
 * @param name  String correspondente ao nome do criador
 * @param email String correspondente ao email do criador
 */
public record UserCreatorRecipeResponseDTO(
        String id,
        String name,
        String email,

        String profilePhoto
) {
}
