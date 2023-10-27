package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record DrinkRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        TypeDrink type,

        String photoURL,
        String videoURL,

        @NotNull
        ArrayList<Ingredient> ingredients,

        @NotBlank
        String instructions
) {
}
