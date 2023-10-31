package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import org.springframework.stereotype.Component;

@Component
public class DrinkMapper {

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
        return new DrinkResponseDTO(
                drink.getId(),
                drink.getName(),
                drink.getTypeDrink(),
                drink.getPhotoURL(),
                drink.getVideoURL(),
                drink.getIngredients(),
                drink.getInstructions(),
                drink.getCreatorID()

        );
    }
}
