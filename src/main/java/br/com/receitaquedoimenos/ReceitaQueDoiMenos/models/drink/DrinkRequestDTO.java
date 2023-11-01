package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record DrinkRequestDTO(
        @NotBlank
        String name,

        @NotNull
        TypeDrink typeDrink,

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
