package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.handler.ErrorResponse;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = "Retorna Informações do Usuário")
    @Parameter(
            name = "userID",
            description = "ID do Usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @GetMapping("/info/{userID}")
    public ResponseEntity<UserResponseDTO> getInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getInfo(userID));
    }

    @Operation(summary = "Retorna as Receitas Criadas pelo Usuário")
    @Parameter(
            name = "userID",
            description = "ID do Usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @GetMapping("/createdRecipes/{userID}")
    public ResponseEntity<List<MealResponseDTO>> getCreatedMealsInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getAllCreatedMeals(userID));
    }

    @Operation(summary = "Retorna as Receitas Favoritas do Usuário")
    @Parameter(
            name = "userID",
            description = "ID do Usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @GetMapping("/favoriteRecipes/{userID}")
    public ResponseEntity<List<MealResponseDTO>> getFavoriteMealsInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getAllFavoriteMeals(userID));
    }

    @Operation(summary = "Retorna os Drinks Criados pelo Usuário")
    @Parameter(
            name = "userID",
            description = "ID do usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @GetMapping("/createdDrinks/{userID}")
    public ResponseEntity<List<DrinkResponseDTO>> getCreatedDrinksInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getAllCreatedDrinks(userID));
    }

    @Operation(summary = "Retorna os Drinks Favoritos pelo usuário")
    @Parameter(
            name = "userID",
            description = "ID do Usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Founded", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "404", description = "Data Not Founded", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @GetMapping("/favoriteDrinks/{userID}")
    public ResponseEntity<List<DrinkResponseDTO>> getFavoriteDrinksInfo(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getAllFavoriteDrinks(userID));
    }

    @Operation(summary = "Atualiza as Informações de Perfil")
    @Parameter(
            name = "userID",
            description = "ID do Usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "userRequestDTO",
            description = "Informações do Usuário Atualizadas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Atualizados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Usuário não Encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Operação não Autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Palavra Proibida Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Operação não Autorizada, Conflito de Informações", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @PutMapping("/profile/{userID}")
    public ResponseEntity<UserResponseDTO> updateProfileInfo(@PathVariable String userID, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.updateProfileInfo(userID, userRequestDTO));
    }

    @Operation(summary = "Deletar Perfil e todas as Suas Refeiçoes e Drinks Associados")
    @Parameter(
            name = "userID",
            description = "ID do Usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário Deletado com Sucesso. Sem Conteúdo"),
            @ApiResponse(responseCode = "404", description = "Usuário não Encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userID) {
        userService.deleteAccount(userID);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Like an annother meal")
    @Parameter(
            name = "userID",
            description = "User id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "mealID",
            description = "Meal id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized Like Operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @Transactional
    @PutMapping("likeMeal/{userID}/{mealID}")
    public ResponseEntity<Void> likeMeal(@PathVariable String userID, @PathVariable String mealID) {
        userService.likeMeal(userID, mealID);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Descurtir uma Refeição")
    @Parameter(
            name = "userID",
            description = "User id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "mealID",
            description = "Meal id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Atualizados com Sucess"),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @Transactional
    @PutMapping("unlikeMeal/{userID}/{mealID}")
    public ResponseEntity<Void> unlikeMeal(@PathVariable String userID, @PathVariable String mealID) {
        userService.unlikeMeal(userID, mealID);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Curtir um Drink de Outro usuário")
    @Parameter(
            name = "userID",
            description = "User id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "drinkID",
            description = "Drink id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Atualizados com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Operação não Autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @PutMapping("likeDrink/{userID}/{drinkID}")
    public ResponseEntity<Void> likeDrink(@PathVariable String userID, @PathVariable String drinkID) {
        userService.likeDrink(userID, drinkID);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Descurtir um Drink")
    @Parameter(
            name = "userID",
            description = "User id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "drinkID",
            description = "Drink id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Atualizados com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @Transactional
    @PutMapping("unlikeDrink/{userID}/{drinkID}")
    public ResponseEntity<Void> unlikeDrink(@PathVariable String userID, @PathVariable String drinkID) {
        userService.unlikeDrink(userID, drinkID);
        return ResponseEntity.ok().build();
    }

}
