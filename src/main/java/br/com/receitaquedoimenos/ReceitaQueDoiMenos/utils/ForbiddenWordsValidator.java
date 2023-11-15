package br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.ForbbidenWordException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserRequestDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A classe ForbiddenWordsValidator fornece métodos para validar a presença de palavras proibidas em textos, incluindo
 * validação de dados de usuários, refeições e bebidas. Mantém uma lista de palavras proibidas e utiliza expressões
 * regulares para verificar a presença dessas palavras nos textos fornecidos. Em caso de correspondência, uma exceção
 * ForbbidenWordException é lançada indicando a presença de palavras proibidas.
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @version 1.0
 * @since 2023.2
 *
 * @see ForbbidenWordException
 */
@Component
public class ForbiddenWordsValidator {

    /**
     * Lista de palavras proibidas.
     */
    private final ArrayList<String> forbiddenWords;

    /**
     * Construtor padrão que inicializa a lista de palavras proibidas.
     */
    public ForbiddenWordsValidator(){
        this.forbiddenWords = new ArrayList<String>();
        this.forbiddenWords.add("pau");
        this.forbiddenWords.add("p4u");
        this.forbiddenWords.add("paunocu");
        this.forbiddenWords.add("pnc");
        this.forbiddenWords.add("cabeçaderola");
        this.forbiddenWords.add("cabecaderola");
        this.forbiddenWords.add("rola");
        this.forbiddenWords.add("pica");
        this.forbiddenWords.add("pika");

        this.forbiddenWords.add("filhadaputa");
        this.forbiddenWords.add("fiadaputa");
        this.forbiddenWords.add("filadaputa");
        this.forbiddenWords.add("feladaputa");
        this.forbiddenWords.add("fidaputa");
        this.forbiddenWords.add("fideputa");
        this.forbiddenWords.add("fdp");
        this.forbiddenWords.add("puta");
        this.forbiddenWords.add("fiderapariga");
        this.forbiddenWords.add("rapariga");
        this.forbiddenWords.add("quenga");

        this.forbiddenWords.add("siririca");
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
        this.forbiddenWords.add("viado");

        this.forbiddenWords.add("javascript");
    }

    /**
     * Valida a presença de palavras proibidas em um texto fornecido.
     *
     * @param text O texto a ser validado.
     * @throws ForbbidenWordException Se uma palavra proibida for encontrada no texto.
     */
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
    private void validateIngredients(ArrayList<String> ingredients) {
        for (String stringToValidate : ingredients) {
            validateString(stringToValidate);
        }
    }
}
