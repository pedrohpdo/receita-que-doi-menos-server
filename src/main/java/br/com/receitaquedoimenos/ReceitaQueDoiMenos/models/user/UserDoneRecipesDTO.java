package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.Meal;

import java.util.ArrayList;

public record UserDoneRecipesDTO(
        ArrayList<Meal> doneMeals
) {
}
