package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserDoneRecipesDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserFavoriteRecipesDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserSaveRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Founded"),
            @ApiResponse(responseCode = "404", description = "User Not Founded on Database")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getInfo(@PathVariable String id) {
        return ResponseEntity.ok(userService.getInfo(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "User Not Founded on Database"),
            @ApiResponse(responseCode = "409", description = "Failed to update. Information Conflict.")})
    @PutMapping("/profile/{id}")
    public ResponseEntity<UserResponseDTO> updateProfileInfo(@PathVariable String id, @RequestBody @Valid UserSaveRequestDTO userSaveRequestDTO) {
        return ResponseEntity.ok(userService.updateProfileInfo(id, userSaveRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
    })
    @PutMapping("/favoriteRecipes/{id}")
    public ResponseEntity<Void> updateFavoriteRecipes(@PathVariable String id, @RequestBody UserFavoriteRecipesDTO
            userFavoriteRecipes) {
        userService.updateFavoriteRecipes(id, userFavoriteRecipes);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Founded"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
    })
    @PutMapping("/doneRecipes/{id}")
    public ResponseEntity<Void> updateDoneRecipes(@PathVariable String id, @RequestBody UserDoneRecipesDTO userDoneRecipes) {
        userService.updateDoneRecipes(id, userDoneRecipes);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User Successfully Deleted. No Content"),
            @ApiResponse(responseCode = "404", description = "User Id Not Founded on Database")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(String id) {
        userService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
    })
    @PutMapping("favoriteRecipe/{idUser}/{idRecipe}")
    public ResponseEntity<Void> favoriteRecipe(@PathVariable String idUser, @PathVariable String idRecipe) {
        userService.favoriteRecipe(idUser, idUser);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
    })
    @PutMapping("unfavoriteRecipe/{idUser}/{idRecipe}")
    public ResponseEntity<Void> unfavoriteRecipe(@PathVariable String idUser, @PathVariable String idRecipe) {
        userService.unfavoriteRecipe(idUser, idUser);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
    })
    @PutMapping("doneRecipe/{idUser}/{idRecipe}")
    public ResponseEntity<Void> doneRecipe(@PathVariable String idUser, @PathVariable String idRecipe) {
        userService.doneRecipe(idUser, idUser);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Data Not Founded on Database"),
    })
    @PutMapping("undoneRecipe/{idUser}/{idRecipe}")
    public ResponseEntity<Void> undoneRecipe(@PathVariable String idUser, @PathVariable String idRecipe) {
        userService.undoneRecipe(idUser, idUser);
        return ResponseEntity.ok().build();
    }
}
