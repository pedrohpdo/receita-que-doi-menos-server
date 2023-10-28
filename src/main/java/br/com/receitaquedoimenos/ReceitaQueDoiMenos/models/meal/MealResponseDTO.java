package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record MealResponseDTO(
        @NotBlank
        String name,

        @NotBlank
        TypeMeal type,

        String photo,
        String video,

        @NotNull
        ArrayList<Ingredient> ingredients,

        @NotBlank
        String instructions
) {
}
