package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.Drink;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.Meal;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseDTO(
        String id,
        String name,
        String email,
        ArrayList<String> createdMealsID,
        ArrayList<String> favoriteMealsID,
        ArrayList<String> createdDrinksID,
        ArrayList<String> favoriteDrinkID

) {}