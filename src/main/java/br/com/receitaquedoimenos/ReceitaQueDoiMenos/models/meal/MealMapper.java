package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserCreatorRecipeResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Classe que Representa um mapeador de entidades, melhorando a abstração dentro dos serviços.
 *
 * @author Pedro Henrique Pereira de Oliveira
 */
@Component
public class MealMapper {

    @Autowired
    UserRepository userRepository;

    /**
     * Retorna uma instância da Meal.class a partir de um record MealRequestDTO
     *
     * @param mealRequestDTO MealRequestDTO recebido
     * @return Instância Meal.class
     */
    public Meal toEntity(MealRequestDTO mealRequestDTO) {
        return new Meal(
                mealRequestDTO.name(),
                mealRequestDTO.typeMeal(),
                mealRequestDTO.photoURL(),
                mealRequestDTO.videoURL(),
                mealRequestDTO.ingredients(),
                mealRequestDTO.instructions(),
                mealRequestDTO.creatorID()
        );
    }

    /**
     * Retorna a Instância de um record MealResponseDTO a partir de um Meal.class
     * <p>
     * Ps: É retornado um conjunto de informações adicionais nessa instância, alocados em um record
     * UserCreatorRecipeResponseDTO, sendo informações adquiridas dentro de UserRepository.class
     *
     * @param meal Meal.class recebido
     * @return Instância MealResponseDTO
     */
    public MealResponseDTO toResponseDTO(Meal meal) {
        User creator = userRepository.findById(meal.getCreatorID()).get();

        return new MealResponseDTO(
                meal.getId(),
                meal.getName(),
                meal.getTypeMeal(),
                meal.getPhotoURL(),
                meal.getVideoURL(),
                meal.getIngredients(),
                meal.getInstructions(),
                new UserCreatorRecipeResponseDTO(meal.getCreatorID(), creator.getName(), creator.getEmail()));
    }

}
