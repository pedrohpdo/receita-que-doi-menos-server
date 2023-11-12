package br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.Drink;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.TypeDrink;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

/**
 * A interface DrinkRepository é uma extensão da interface MongoRepository, fornecendo métodos para acessar e manipular
 * dados relacionados às bebidas no banco de dados MongoDB.
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @version 1.0
 * @since 2023.2
 */
public interface DrinkRepository extends MongoRepository<Drink, String> {
    /**
     * Recupera uma lista de bebidas com um determinado tipo de bebida.
     *
     * @param type O tipo de bebida a ser filtrado.
     * @return Uma lista de bebidas do tipo especificado.
     */
    ArrayList<Drink> findAllByTypeDrink(TypeDrink type);

    /**
     * Recupera uma lista de bebidas com um determinado nome, ignorando maiúsculas e minúsculas.
     *
     * @param name O nome da bebida a ser pesquisado.
     * @return Uma lista de bebidas com o nome especificado (ignorando maiúsculas e minúsculas).
     */
    ArrayList<Drink> findAllByNameIgnoreCase(String name);


}
