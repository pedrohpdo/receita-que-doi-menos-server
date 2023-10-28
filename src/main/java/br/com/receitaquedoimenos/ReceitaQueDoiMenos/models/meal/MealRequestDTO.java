package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record MealRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        TypeMeal type,

        String photoURL,
        String videoURL,

        @NotNull
        ArrayList<Ingredient> ingredients,

        @NotBlank
        String instructions
) {
}
