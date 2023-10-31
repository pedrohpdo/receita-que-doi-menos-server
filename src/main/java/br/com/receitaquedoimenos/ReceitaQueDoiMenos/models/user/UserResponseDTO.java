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
        ArrayList<Meal> createdMeals,
        ArrayList<Meal> favoriteMeals,
        ArrayList<Drink> createdDrinks,
        ArrayList<Drink> favoriteDrinks

) {}