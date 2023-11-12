package br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.Meal;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.TypeMeal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * A interface MealRepository é uma extensão da interface MongoRepository, fornecendo métodos para acessar e manipular
 * dados relacionados às refeições no banco de dados MongoDB.
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @version 1.0
 * @since 2023.2
 */
public interface MealRepository extends MongoRepository<Meal, String> {
    /**
     * Recupera uma lista de refeições com um determinado tipo de refeição.
     *
     * @param type O tipo de refeição a ser filtrado.
     * @return Uma lista de refeições do tipo especificado.
     */
    List<Meal> findAllByTypeMeal(TypeMeal type);

    /**
     * Recupera uma lista de refeições com um determinado nome, ignorando maiúsculas e minúsculas.
     *
     * @param name O nome da refeição a ser pesquisado.
     * @return Uma lista de refeições com o nome especificado (ignorando maiúsculas e minúsculas).
     */
    List<Meal> findAllByNameIgnoreCase(String name);

}
