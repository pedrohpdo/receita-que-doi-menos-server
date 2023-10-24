package br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface RecipeRepository extends MongoRepository<Recipe, String>{
    List<Recipe> findAllByType(String type);
}
