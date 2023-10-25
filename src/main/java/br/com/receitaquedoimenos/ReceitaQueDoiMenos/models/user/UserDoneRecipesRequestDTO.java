package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.Recipe;

import java.util.ArrayList;

public record UserDoneRecipesRequestDTO(
        ArrayList<Recipe> doneRecipes
) {
}
