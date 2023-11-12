package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserCreatorRecipeResponseDTO;

import java.util.ArrayList;

/**
 * Informações referentes a um drink criados ou atualizados retornados pelo sistema
 *
 * @param name         String correspondente ao nome do Drink
 * @param typeDrink    Enumerador correspondente ao tipo de Drinks
 * @param photoURL     String correspondente a URL da foto do Drink
 * @param videoURL     String correspondente a URL do vídeo do Drink
 * @param ingredients  Arraylist que corresponde aos ingredientes do Drink
 * @param instructions String correspondente ao modo de preparo de cada Drink
 * @param creator      Informações relacionadas ao criador de cada drink
 *
 * @author Pedro Henrique Pereira de Oliveira
 */
public record DrinkResponseDTO(
        String id,
        String name,
        TypeDrink typeDrink,
        String photoURL,
        String videoURL,
        ArrayList<Ingredient> ingredients,
        String instructions,
        UserCreatorRecipeResponseDTO creator
) {
}
