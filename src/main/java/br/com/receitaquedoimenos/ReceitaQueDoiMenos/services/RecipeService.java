package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.RecipeRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
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

    public RecipeResponseDTO createRecipe(RecipeRequestDTO recipeRequestDTO) {
        return recipeMapper.toResponseDTO(recipeRepository.save(recipeMapper.toEntity(recipeRequestDTO)));
    }

    public List<RecipeResponseDTO> getRecipesByName(String name) {
        return recipeRepository.findAllByNameIgnoreCase(name)
                .stream()
                .map(recipeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public List<RecipeResponseDTO> getRecipesByType(TypeFood type) {
        return recipeRepository.findAllByTypeFood(type)
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
                    recipeFounded.setPhotoURL(recipeRequestDTO.photoURL());
                    recipeFounded.setVideoURL(recipeRequestDTO.videoURL());
                    recipeFounded.setInstructions(recipeRequestDTO.instructions());
                    recipeFounded.setIngredients(recipeRequestDTO.ingredients());

                    return recipeMapper.toResponseDTO(recipeRepository.save(recipeFounded));
                }).orElseThrow(() -> new DataNotFoundException("Recipe Not Found"));
    }

    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);
    }
}
