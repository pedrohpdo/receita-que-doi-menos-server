package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import org.springframework.stereotype.Component;

@Component
public class DrinkMapper {

    public Drink toEntity(DrinkRequestDTO drinkRequestDTO) {
        return new Drink(
                drinkRequestDTO.name(),
                drinkRequestDTO.type(),
                drinkRequestDTO.photoURL(),
                drinkRequestDTO.videoURL(),
                drinkRequestDTO.ingredients(),
                drinkRequestDTO.instructions()
        );
    }

    public DrinkResponseDTO toResponseDTO(Drink drink) {
        return new DrinkResponseDTO(
                drink.getId(),
                drink.getName(),
                drink.getType(),
                drink.getPhotoURL(),
                drink.getVideoURL(),
                drink.getIngredients(),
                drink.getInstructions()

        );
    }
}
