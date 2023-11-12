package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

/**
 * Informações referentes a um drink criados ou atualizados requeridos dentro do sistema
 *
 * @param name         String correspondente ao nome do Drink
 * @param typeDrink    Enumerador correspondente ao tipo de Drinks
 * @param photoURL     String correspondente a URL da foto do Drink
 * @param videoURL     String correspondente a URL do vídeo do Drink
 * @param ingredients  Arraylist que corresponde aos ingredientes do Drink
 * @param instructions String correspondente ao modo de preparo de cada Drink
 * @param creatorID    String correspondente ao ID do Criador
 * @author Pedro Henrique Pereira de Oliveira
 */
public record DrinkRequestDTO(
        @NotBlank String name,
        @NotNull TypeDrink typeDrink,
        String photoURL,
        String videoURL,
        @NotNull ArrayList<Ingredient> ingredients,
        @NotBlank String instructions,
        @NotBlank String creatorID
) {
}
