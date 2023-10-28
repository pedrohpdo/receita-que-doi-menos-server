package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import org.springframework.stereotype.Component;

@Component
public class MealMapper {

    public Meal toEntity(MealRequestDTO requestDTO) {
        return new Meal(
                requestDTO.name(),
                requestDTO.type(),
                requestDTO.photoURL(),
                requestDTO.videoURL(),
                requestDTO.ingredients(),
                requestDTO.instructions()
        );
    }

    public MealResponseDTO toResponseDTO(Meal meal) {
        return new MealResponseDTO(
                meal.getName(),
                meal.getType(),
                meal.getPhotoURL(),
                meal.getVideoURL(),
                meal.getIngredients(),
                meal.getInstructions()
        );
    }
}
