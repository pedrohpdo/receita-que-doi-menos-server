package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MealMapper {

    public Meal toEntity(MealRequestDTO requestDTO) {
        return new Meal(
                requestDTO.name(),
                requestDTO.typeMeal(),
                requestDTO.photoURL(),
                requestDTO.videoURL(),
                requestDTO.ingredients(),
                requestDTO.instructions(),
                requestDTO.creatorID()
        );
    }

    public MealResponseDTO toResponseDTO(Meal meal) {
        return new MealResponseDTO(
                meal.getName(),
                meal.getTypeMeal(),
                meal.getPhotoURL(),
                meal.getVideoURL(),
                meal.getIngredients(),
                meal.getInstructions(),
                meal.getCreatorID()
        );
    }

}
