package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

/**
 * Informações referentes a um drink criados ou atualizados requeridos dentro do sistema
 *
 * @param name         String referente ao nome
 * @param typeMeal     Enum referente ao tipo da refeição
 * @param photoURL     String URL de uma imagem
 * @param videoURL     String URL de um vídeo
 * @param ingredients  ArrayList contendo os ingredientes
 * @param instructions String referente ao modo de preparo
 * @param creatorID    String referente ao criador da refeição
 * @author Pedro Henrique Pereira de Oliveira
 */
public record MealRequestDTO(
        @NotBlank
        String name,

        @NotNull
        TypeMeal typeMeal,

        String photoURL,
        String videoURL,

        @NotNull
        ArrayList<Ingredient> ingredients,

        @NotBlank
        String instructions,

        @NotBlank
        String creatorID
) {
}
