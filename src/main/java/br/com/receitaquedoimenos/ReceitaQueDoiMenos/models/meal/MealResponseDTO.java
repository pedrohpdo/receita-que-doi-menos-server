package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserCreatorRecipeResponseDTO;

import java.util.ArrayList;

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
