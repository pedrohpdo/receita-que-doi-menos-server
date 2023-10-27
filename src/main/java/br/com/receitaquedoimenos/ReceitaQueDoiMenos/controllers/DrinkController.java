package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.TypeDrink;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.DrinkService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/drinks")
public class DrinkController {

    @Autowired
    DrinkService drinkService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data successfully registered"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to Create New Data"),
            @ApiResponse(responseCode = "409", description = "Failed to register. Information Conflict.")
    })
    @PostMapping
    public ResponseEntity<DrinkResponseDTO> createDrink(@RequestBody @Valid DrinkRequestDTO drinkRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(drinkService.createRecipe(drinkRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded")
    })
    @GetMapping("/byId/{id}")
    public ResponseEntity<DrinkResponseDTO> getDrinkById(@PathVariable String id){
        return ResponseEntity.ok(drinkService.getDrinkById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Founded"),
    })
    @GetMapping("/all")
    public ResponseEntity<List<DrinkResponseDTO>> getAllDrinks(){
        return ResponseEntity.ok(drinkService.getAllDrinks());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded"),
    })
    @GetMapping("/byType/{type}")
    public ResponseEntity<List<DrinkResponseDTO>> getDrinksByType(@PathVariable TypeDrink type){
        return ResponseEntity.ok(drinkService.getDrinksByType(type));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded"),
    })
    @GetMapping("/byName/{name}")
    public ResponseEntity<List<DrinkResponseDTO>> getDrinksByName(@PathVariable String name) {
        return ResponseEntity.ok(drinkService.getDrinksByName(name));

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<DrinkResponseDTO> updateDrink(@PathVariable String id, @RequestBody @Valid DrinkRequestDTO drinkRequestDTO) {
        return ResponseEntity.ok(drinkService.updateDrink(id, drinkRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Data Successfully Deleted. No Content."),
            @ApiResponse(responseCode = "404", description = "Data Not Founded"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable String id) {
        drinkService.deleteDrink(id);
        return ResponseEntity.noContent().build();
    }
}
