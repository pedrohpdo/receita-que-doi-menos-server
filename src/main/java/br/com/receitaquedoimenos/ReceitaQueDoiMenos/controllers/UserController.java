package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded")
    })
    @Transactional
    @GetMapping("/info/{userID}")
    public ResponseEntity<UserResponseDTO> getInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getInfo(userID));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded")
    })
    @Transactional
    @GetMapping("/createdRecipes/{userID}")
    public ResponseEntity<List<MealResponseDTO>> getCreatedMealsInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getAllCreatedMeals(userID));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded")
    })
    @Transactional
    @GetMapping("/favoriteRecipes/{userID}")
    public ResponseEntity<List<MealResponseDTO>> getFavoriteMealsInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getAllFavoriteMeals(userID));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded")
    })
    @Transactional
    @GetMapping("/createdDrinks/{userID}")
    public ResponseEntity<List<DrinkResponseDTO>> getCreatedDrinksInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getAllCreatedDrinks(userID));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded")
    })
    @Transactional
    @GetMapping("/favoriteDrinks/{userID}")
    public ResponseEntity<List<DrinkResponseDTO>> getFavoriteDrinksInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getAllFavoriteDrinks(userID));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Founded"),
            @ApiResponse(responseCode = "404", description = "Cannot Found Any Data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized Update Operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden Word Founded"),
            @ApiResponse(responseCode = "409", description = "Already Exists An Email on System")
    })
    @Transactional
    @PutMapping("/profile/{userID}")
    public ResponseEntity<UserResponseDTO> updateProfileInfo(@PathVariable String userID, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.updateProfileInfo(userID, userRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User Successfully Deleted. No Content"),
            @ApiResponse(responseCode = "404", description = "User Not Founded")
    })
    @Transactional
    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUser(String userID) {
        userService.deleteAccount(userID);
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
            @ApiResponse(responseCode = "401", description = "Unauthorized Like Operation"),
    })
    @Transactional
    @PutMapping("likeMeal/{userID}/{mealID}")
    public ResponseEntity<Void> likeMeal(@PathVariable String userID, @PathVariable String mealID) {
        userService.likeMeal(userID, mealID);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
    })
    @Transactional
    @PutMapping("unlikeMeal/{userID}/{mealID}")
    public ResponseEntity<Void> unlikeMeal(@PathVariable String userID, @PathVariable String mealID) {
        userService.unlikeMeal(userID, mealID);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
            @ApiResponse(responseCode = "401", description = "Unauthorized Like Operation")
    })
    @Transactional
    @PutMapping("likeDrink/{userID}/{drinkID}")
    public ResponseEntity<Void> likeDrink(@PathVariable String userID, @PathVariable String drinkID) {
        userService.likeDrink(userID, drinkID);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
    })
    @Transactional
    @PutMapping("unlikeDrink/{userID}/{drinkID}")
    public ResponseEntity<Void> unlikeDrink(@PathVariable String userID, @PathVariable String drinkID) {
        userService.unlikeDrink(userID, drinkID);
        return ResponseEntity.ok().build();
    }

}
