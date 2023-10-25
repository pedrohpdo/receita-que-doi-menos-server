package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.Recipe;

import java.util.ArrayList;

public record UserFavoriteRecipesRequestDTO(
        ArrayList<Recipe> favoriteRecipes
) {}
