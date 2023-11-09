package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserCreatorRecipeResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MealMapper {

    @Autowired
    UserRepository userRepository;

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
        User creator = userRepository.findById(meal.getCreatorID()).get();

        return new MealResponseDTO(
                meal.getName(),
                meal.getTypeMeal(),
                meal.getPhotoURL(),
                meal.getVideoURL(),
                meal.getIngredients(),
                meal.getInstructions(),
                new UserCreatorRecipeResponseDTO(meal.getCreatorID(), creator.getName(), creator.getEmail()));
    }

}
