package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe;

import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    public Recipe toEntity(RecipeRequestDTO requestDTO) {
        return new Recipe(
                requestDTO.name(),
                requestDTO.type(),
                requestDTO.photoURL(),
                requestDTO.videoURL(),
                requestDTO.ingredients(),
                requestDTO.instructions()
        );
    }

    public RecipeResponseDTO toResponseDTO(Recipe recipe) {
        return new RecipeResponseDTO(
                recipe.getName(),
                recipe.getType(),
                recipe.getPhotoURL(),
                recipe.getVideoURL(),
                recipe.getIngredients(),
                recipe.getInstructions()
        );
    }
}
