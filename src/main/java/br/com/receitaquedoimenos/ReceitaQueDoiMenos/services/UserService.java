package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.Recipe;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.RecipeRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RecipeRepository recipeRepository;

    public UserResponseDTO get(String id) {
        return userRepository.findById(id)
                .map(userFounded -> userMapper.toResponseDTO(userFounded))
                .orElseThrow(() -> new NullPointerException("User Not Founded"));
    }

    public UserResponseDTO updateProfileSettings(String id, UserSaveRequestDTO userSaveRequestDTO) {
        return userRepository.findById(id)
                .map(userFounded -> {
                    CharSequence checkPassword = BCrypt.withDefaults().hashToString(12, userSaveRequestDTO.password().toCharArray());
                    if (!checkPassword.equals(userFounded.getPassword())) {
                        userFounded.setPassword(checkPassword);
                    }

                    userFounded.setName(userSaveRequestDTO.name());
                    userFounded.setEmail(userSaveRequestDTO.email());

                    userRepository.save(userFounded);
                    return userMapper.toResponseDTO(userFounded);
                })
                .orElseThrow(() -> new NullPointerException("User Not Founded"));
    }

    public void updateFavoriteRecipes(String id, UserFavoriteRecipesRequestDTO userFavoriteRecipes) {
        userRepository.findById(id)
                .ifPresentOrElse(userFounded -> {
                    userFounded.setFavoriteRecipes(userFavoriteRecipes.favoriteRecipes());
                    userRepository.save(userFounded);
                }, () -> new NullPointerException("User Not Founded"));
    }


    public void updateDoneRecipes(String id, UserDoneRecipesRequestDTO userRecipes) {
        userRepository.findById(id)
                .ifPresentOrElse(userFounded -> {
                    userFounded.setDoneRecipes(userRecipes.doneRecipes());
                    userRepository.save(userFounded);
                }, () -> new NullPointerException("User Not Founded"));
    }

    public void delete(String id) {
        userRepository.delete(
                userRepository.findById(id)
                        .orElseThrow(() -> new NullPointerException("User not Founded"))
        );
    }

    public void favoriteRecipe(String idUser, String idRecipe) {
        Recipe recipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new RuntimeException());

        userRepository.findById(idUser)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteRecipes().add(recipe);
                    userRepository.save(userFounded);

                }, () -> new RuntimeException());
    }
}
