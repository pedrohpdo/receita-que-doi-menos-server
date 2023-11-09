package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserCreatorRecipeResponseDTO;

import java.util.ArrayList;

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
