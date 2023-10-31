package br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.ForbbidenWordException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserRequestDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ForbiddenWordsValidator {

    private ArrayList<String> forbiddenWords;

    public ForbiddenWordsValidator(){
        this.forbiddenWords = new ArrayList<String>();
        this.forbiddenWords.add("pau");
        this.forbiddenWords.add("rola");
        this.forbiddenWords.add("pica");
        this.forbiddenWords.add("pika");

        this.forbiddenWords.add("puta");
        this.forbiddenWords.add("rapariga");
        this.forbiddenWords.add("quenga");

        this.forbiddenWords.add("siririca");
        this.forbiddenWords.add("pariu");
        this.forbiddenWords.add("transar");
        this.forbiddenWords.add("cu");
        this.forbiddenWords.add("buceta");
        this.forbiddenWords.add("bct");

        this.forbiddenWords.add("caralho");
        this.forbiddenWords.add("carai");
        this.forbiddenWords.add("porra");
        this.forbiddenWords.add("merda");
        this.forbiddenWords.add("imbecil");

        this.forbiddenWords.add("foda");
        this.forbiddenWords.add("foda-se");
        this.forbiddenWords.add("fodase");
        this.forbiddenWords.add("fodasse");
    }

    public void validateString(String text) {
        for (String word:
             this.forbiddenWords) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher match = pattern.matcher(text);

            if(match.find()) {
                throw new ForbbidenWordException("Forbidden Word Founded");
            }
        }
    }

    public void validateUserData(UserRequestDTO user){
        validateString(user.name());
        validateString(user.email());
        validateString(user.password());
    }

    public void validateMeal(MealRequestDTO meal) {
        validateString(meal.name());
        validateString(meal.instructions());
        validateIngredients(meal.ingredients());
    }

    public void validateDrink(DrinkRequestDTO drink) {
        validateString(drink.name());
        validateString(drink.instructions());
        validateIngredients(drink.ingredients());
    }
    private void validateIngredients(ArrayList<Ingredient> ingredients) {
        for (Ingredient stringToValidate : ingredients) {
            validateString(stringToValidate.getName());
        }
    }
}
