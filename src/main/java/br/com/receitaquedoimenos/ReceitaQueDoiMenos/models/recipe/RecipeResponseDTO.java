package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record RecipeResponseDTO(
        @NotBlank
        String name,

        @NotBlank
        String type,

        String photo,
        String video,

        @NotNull
        ArrayList<String> ingredients,

        @NotBlank
        String instructions
) {
}
