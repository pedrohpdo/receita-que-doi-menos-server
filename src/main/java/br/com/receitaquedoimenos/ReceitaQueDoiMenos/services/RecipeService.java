package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.RecipeMapper;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.RecipeRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.RecipeResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeMapper recipeMapper;

    public RecipeResponseDTO createRecipe(RecipeRequestDTO recipeRequestDTO){
        return recipeMapper.toResponseDTO(recipeRepository.save(recipeMapper.toEntity(recipeRequestDTO)));
    }

    public RecipeResponseDTO getRecipe(String id) {
        return recipeRepository.findById(id)
                .map(value -> recipeMapper.toResponseDTO(value))
                .orElseThrow(() -> new NullPointerException("Recipe Not Found"));
    }


    public List<RecipeResponseDTO> getRecipesByType(String type) {
        return recipeRepository.findAllByType(type)
                .stream()
                .map(recipeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<RecipeResponseDTO> getAll() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public RecipeResponseDTO updateRecipe(String id, RecipeRequestDTO recipeRequestDTO) {
        return recipeRepository.findById(id)
                .map(recipeFounded -> {
                    recipeFounded.setName(recipeRequestDTO.name());
                    recipeFounded.setType(recipeRequestDTO.type());
                    recipeFounded.setPhoto(recipeRequestDTO.photo());
                    recipeFounded.setVideo(recipeRequestDTO.video());
                    recipeFounded.setInstructions(recipeRequestDTO.instructions());
                    recipeFounded.setIngredients(recipeRequestDTO.ingredients());

                    return recipeMapper.toResponseDTO(recipeRepository.save(recipeFounded));
                }).orElseThrow(() -> new NullPointerException("Recipe Not Found"));
    }

    public void deleteRecipe(String id){
        recipeRepository.deleteById(id);
    }
}
