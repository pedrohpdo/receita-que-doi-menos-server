package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserCreatorRecipeResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DrinkMapper {

    @Autowired
    UserRepository userRepository;

    public Drink toEntity(DrinkRequestDTO drinkRequestDTO) {
        return new Drink(
                drinkRequestDTO.name(),
                drinkRequestDTO.typeDrink(),
                drinkRequestDTO.photoURL(),
                drinkRequestDTO.videoURL(),
                drinkRequestDTO.ingredients(),
                drinkRequestDTO.instructions(),
                drinkRequestDTO.creatorID()
        );
    }

    public DrinkResponseDTO toResponseDTO(Drink drink) {

        User creator = userRepository.findById(drink.getCreatorID()).get();

        return new DrinkResponseDTO(
                drink.getId(),
                drink.getName(),
                drink.getTypeDrink(),
                drink.getPhotoURL(),
                drink.getVideoURL(),
                drink.getIngredients(),
                drink.getInstructions(),
                new UserCreatorRecipeResponseDTO(drink.getCreatorID(), creator.getName(), creator.getEmail())
        );
    }
}
