package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.TypeMeal;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.MealService;
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
public class MealController {

    @Autowired
    MealService mealService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data successfully registered"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to Create New Data"),
            @ApiResponse(responseCode = "409", description = "Failed to register. Information Conflict.")
    })
    @PostMapping
    public ResponseEntity<MealResponseDTO> createMeal(@RequestBody @Valid MealRequestDTO mealRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(mealService.createMeal(mealRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data Founded"),
    })
    @GetMapping("/all")
    public ResponseEntity<List<MealResponseDTO>> getAllRecipes(){
        return ResponseEntity.ok(mealService.getAll());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Cannot Found Any Data"),
    })
    @GetMapping("/byType/{typeMeal}")
    public ResponseEntity<List<MealResponseDTO>> getRecipesByTypeMeal(@PathVariable TypeMeal typeMeal) {
        return ResponseEntity.ok(mealService.getMealsByTypeMeal(typeMeal));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Cannot Found Any Data"),
    })
    @GetMapping("byName/{name}")
    public ResponseEntity<List<MealResponseDTO>> getMealsByName(@PathVariable String name){
        return ResponseEntity.ok(mealService.getMealsByName(name));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Cannot Found Any Data"),
    })
    @GetMapping("byMealID/{mealID}")
    public ResponseEntity<MealResponseDTO> getMealsById(@PathVariable String mealID){
        return ResponseEntity.ok(mealService.getMealsById(mealID));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Cannot Found Any Data"),
            @ApiResponse(responseCode = "409", description = "Failed to update. Information Conflict.")
    })
    @PutMapping("/{mealID}/{userID}")
    public ResponseEntity<MealResponseDTO> updateRecipe(@PathVariable String mealID, String userID,
                                                        @RequestBody @Valid MealRequestDTO mealRequestDTO) {
        return ResponseEntity.ok(mealService.updateMealInfo(mealID, userID, mealRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Data Successfully Deleted. No Content."),
            @ApiResponse(responseCode = "404", description = "Meal Id Not Founded on Database"),
    })
    @DeleteMapping("/{mealID}/{userID}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String mealID, String userID) {
        mealService.deleteMeal(mealID, userID);
        return ResponseEntity.noContent().build();
    }
}
