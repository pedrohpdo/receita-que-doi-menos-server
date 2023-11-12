package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserCreatorRecipeResponseDTO;

import java.util.ArrayList;

/**
 * Informações referentes a um drink criados ou atualizados retornados pelo sistema
 *
 * @param name         String referente ao nome
 * @param typeMeal     Enum referente ao tipo da refeição
 * @param photo        String URL de uma imagem
 * @param video        String URL de um vídeo
 * @param ingredients  ArrayList contendo os ingredientes
 * @param instructions String referente ao modo de preparo
 * @param creator      String referente ao criador da refeição
 * @author Pedro Henrique Pereira de Oliveira
 */
public record MealResponseDTO(
        String name,
        TypeMeal typeMeal,
        String photo,
        String video,
        ArrayList<Ingredient> ingredients,
        String instructions,
        UserCreatorRecipeResponseDTO creator
) {
}
