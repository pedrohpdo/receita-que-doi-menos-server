package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserDoneRecipesRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserFavoriteRecipesRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserSaveRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to Create User"),
            @ApiResponse(responseCode = "409", description = "Failed to register. Information Conflict.")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserSaveRequestDTO userSaveRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userSaveRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Founded"),
            @ApiResponse(responseCode = "404", description = "User Not Founded on Database")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Sucessfully Updated"),
            @ApiResponse(responseCode = "404", description = "User Not Founded on Database"),
            @ApiResponse(responseCode = "409", description = "Failed to update. Information Conflict.")})
    @PutMapping("/profile/{id}")
    public ResponseEntity<UserResponseDTO> updateProfile(@PathVariable String id, @RequestBody @Valid UserSaveRequestDTO userSaveRequestDTO) {
        return ResponseEntity.ok(userService.updateProfileSettings(id, userSaveRequestDTO));
    }

    @PutMapping("/favoriteRecipes/{id}")
    public ResponseEntity<Void> updateFavoriteRecipes(@PathVariable String id, @RequestBody UserFavoriteRecipesRequestDTO
            userFavoriteRecipes) {
        userService.updateFavoriteRecipes(id, userFavoriteRecipes);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/doneRecipes/{id}")
    public ResponseEntity<Void> updateDoneRecipes(@PathVariable String id, @RequestBody UserDoneRecipesRequestDTO userDoneRecipes) {
        userService.updateDoneRecipes(id, userDoneRecipes);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User Sucessfully Deleted. No More Content"),
            @ApiResponse(responseCode = "404", description = "User Not Founded on Database")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(String id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
