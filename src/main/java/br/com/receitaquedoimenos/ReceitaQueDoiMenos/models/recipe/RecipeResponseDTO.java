package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record RecipeResponseDTO(
        @NotBlank
        String name,

        @NotBlank
        TypeFood type,

        String photo,
        String video,

        @NotNull
        ArrayList<Ingredient> ingredients,

        @NotBlank
        String instructions
) {
}
