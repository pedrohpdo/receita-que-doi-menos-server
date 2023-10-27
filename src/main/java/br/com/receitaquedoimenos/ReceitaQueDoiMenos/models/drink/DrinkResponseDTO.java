package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;

import java.util.ArrayList;

public record DrinkResponseDTO(
        String id,
        String name,
        TypeDrink type,
        String photoURL,
        String videoURL,
        ArrayList<Ingredient> ingredients,
        String instructions
) {
}
