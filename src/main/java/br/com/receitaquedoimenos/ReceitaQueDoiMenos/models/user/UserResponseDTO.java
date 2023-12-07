package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.Drink;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.Meal;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

/**
 * Informações referentes a um usuário criado ou atualizados requeridos dentro do sistema
 *
 * @param name            String referente ao nome do usuário
 * @param email           String referente ao email do usuário
 * @param createdMealsID  Lista com ID's das receitas criadas
 * @param favoriteMealsID Lista com ID's das receitas favoritadas
 * @param createdDrinksID Lista com ID's dos drinks criados
 * @param favoriteDrinkID Lista com ID's dos drinks favoritados
 * @author Pedro Henrique Pereira de Oliveira
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseDTO(
        String id,
        String name,
        String email,
        String profilePhoto,
        ArrayList<String> createdMealsID,
        ArrayList<String> favoriteMealsID,
        ArrayList<String> createdDrinksID,
        ArrayList<String> favoriteDrinkID

) {
}