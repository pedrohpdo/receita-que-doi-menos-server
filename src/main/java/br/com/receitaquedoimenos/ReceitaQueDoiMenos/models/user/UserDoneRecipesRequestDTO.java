package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import java.util.ArrayList;

public record UserDoneRecipesRequestDTO(
        ArrayList<String> doneRecipes
) {
}
