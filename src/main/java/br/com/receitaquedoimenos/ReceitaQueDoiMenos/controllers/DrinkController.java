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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



import java.util.List;

@RestController
@RequestMapping("/drinks")
public class DrinkController {

    @Autowired
    DrinkService drinkService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data successfully registered"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to Create New Data"),
            @ApiResponse(responseCode = "409", description = "Failed to register. Information Conflict"),
            @ApiResponse(responseCode = "404", description = "User Not Found"),
            @ApiResponse(responseCode = "403", description = "Forbidden Word Founded")
    })
    @PostMapping
    public ResponseEntity<DrinkResponseDTO> createDrink(@RequestBody @Valid DrinkRequestDTO drinkRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(drinkService.createDrink(drinkRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded")
    })
    @GetMapping("/byId/{drinkID}")
    public ResponseEntity<DrinkResponseDTO> getDrinkById(@PathVariable String drinkID){
        return ResponseEntity.ok(drinkService.getDrinkById(drinkID));
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
    @GetMapping("/byType/{typeDrink}")
    public ResponseEntity<List<DrinkResponseDTO>> getDrinksByType(@PathVariable TypeDrink typeDrink){
        return ResponseEntity.ok(drinkService.getDrinksByType(typeDrink));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded"),
    })
    @GetMapping("/byName/{drinkName}")
    public ResponseEntity<List<DrinkResponseDTO>> getDrinksByName(@PathVariable String drinkName) {
        return ResponseEntity.ok(drinkService.getDrinksByName(drinkName));

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Cannot Found Any Data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized Update Operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden Word Founded")
    })
    @PutMapping("/{drinkID}/{userID}")
    public ResponseEntity<DrinkResponseDTO> updateDrink(@PathVariable String drinkID, @PathVariable String userID, @RequestBody @Valid DrinkRequestDTO drinkRequestDTO) {
        return ResponseEntity.ok(drinkService.updateDrink(drinkID, userID, drinkRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Data Successfully Deleted. No Content."),
            @ApiResponse(responseCode = "404", description = "Meal Id Not Founded on Database"),
            @ApiResponse(responseCode = "401", description = "Unauthorized Delete Operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden Word Founded")
    })
    @Transactional
    @DeleteMapping("/{drinkID}/{userID}")
    public ResponseEntity<Void> deleteDrink(@PathVariable String drinkID, @PathVariable String userID) {
        drinkService.deleteDrink(drinkID, userID);
        return ResponseEntity.noContent().build();
    }
}
