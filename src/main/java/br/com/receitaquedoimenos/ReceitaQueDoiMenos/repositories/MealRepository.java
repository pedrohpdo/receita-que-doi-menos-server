package br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.Meal;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.TypeMeal;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface MealRepository extends MongoRepository<Meal, String>{
    List<Meal> findAllByTypeMeal(TypeMeal type);
    List<Meal> findAllByNameIgnoreCase(String name);

}
