package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.RecipeRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.RecipeResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.TypeFood;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.RecipeService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data successfully registered"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to Create New Data"),
            @ApiResponse(responseCode = "409", description = "Failed to register. Information Conflict.")
    })
    @PostMapping
    public ResponseEntity<RecipeResponseDTO> createRecipe(@Valid @RequestBody RecipeRequestDTO recipeRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.createRecipe(recipeRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data Founded"),
    })
    @GetMapping("/all")
    public ResponseEntity<List<RecipeResponseDTO>> getAllRecipes(){
        return ResponseEntity.ok(recipeService.getAll());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Cannot Found Any Data"),
    })
    @GetMapping("/byType/{type}")
    public ResponseEntity<List<RecipeResponseDTO>> getRecipesByType(@PathVariable TypeFood typeFood) {
        return ResponseEntity.ok(recipeService.getRecipesByType(typeFood));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Cannot Found Any Data"),
    })
    @GetMapping("byId/{name}")
    public ResponseEntity<List<RecipeResponseDTO>> getRecipeByName(@PathVariable String name){
        return ResponseEntity.ok(recipeService.getRecipesByName(name));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Cannot Found Any Data"),
            @ApiResponse(responseCode = "409", description = "Failed to update. Information Conflict.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> updateRecipe(@PathVariable String id, @RequestBody @Valid RecipeRequestDTO recipeRequestDTO) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipeRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Data Successfully Deleted. No Content."),
            @ApiResponse(responseCode = "404", description = "Recipe Id Not Founded on Database"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
