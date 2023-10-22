package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserDoneRecipesRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserFavoriteRecipesRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserSaveRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserSaveRequestDTO userSaveRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userSaveRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(userService.get(id));
    }

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
}
