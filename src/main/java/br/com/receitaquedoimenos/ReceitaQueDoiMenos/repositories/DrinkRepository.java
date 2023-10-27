package br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.Drink;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.TypeDrink;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.ArrayList;

public interface DrinkRepository extends MongoRepository<Drink, String> {
    ArrayList<Drink> findAllByTypeDrink(TypeDrink type);

    ArrayList<Drink> findAllByNameIgnoreCase(String name);
}
