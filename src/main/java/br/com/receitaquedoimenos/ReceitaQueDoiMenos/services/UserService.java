package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.ConflictDataException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.Meal;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.MealRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    UserMapper userMapper;


    public UserResponseDTO getInfo(String id) {
        return userRepository.findById(id)
                .map(userFounded -> userMapper.toResponseDTO(userFounded))
                .orElseThrow(() -> new DataNotFoundException("User Not Founded"));
    }

    public UserResponseDTO updateProfileInfo(String id, UserSaveRequestDTO userSaveRequestDTO) {
        if (userRepository.existsByEmailAndIdNot(userSaveRequestDTO.email(), id)) {
            throw new ConflictDataException("Already Exists An Email on System.");
        }

        return userRepository.findById(id)
                .map(userFounded -> {
                    //check encript password
                    userFounded.setPassword(userFounded.getPassword());
                    userFounded.setName(userSaveRequestDTO.name());
                    userFounded.setEmail(userSaveRequestDTO.email());

                    userRepository.save(userFounded);
                    return userMapper.toResponseDTO(userFounded);
                })
                .orElseThrow(() -> new DataNotFoundException("User Not Founded"));
    }

    public UserFavoriteRecipesDTO updateFavoriteRecipes(String id, UserFavoriteRecipesDTO userFavoriteRecipes) {
        return userRepository.findById(id)
                .map(userFounded -> {
                    userFounded.setFavoriteMeals(userFavoriteRecipes.favoriteMeals());
                    userRepository.save(userFounded);
                    return userFavoriteRecipes;

                }).orElseThrow(() -> new DataNotFoundException("User Not Founded"));
    }


    public UserDoneRecipesDTO updateDoneRecipes(String id, UserDoneRecipesDTO userDoneRecipes) {
        return userRepository.findById(id)
                .map(userFounded -> {
                    userFounded.setDoneMeals(userDoneRecipes.doneMeals());
                    userRepository.save(userFounded);
                    return userDoneRecipes;

                }).orElseThrow(() -> new DataNotFoundException("User Not Founded"));
    }

    public void deleteAccount(String id) {
        userRepository.delete(
                userRepository.findById(id)
                        .orElseThrow(() -> new DataNotFoundException("User not Founded"))
        );
    }


    public void favoriteRecipe(String idUser, String idRecipe) {
        Meal meal = mealRepository.findById(idRecipe)
                .orElseThrow(() -> new DataNotFoundException("Recipe Not Founded"));

        userRepository.findById(idUser)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteMeals().add(meal);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    public void unfavoriteRecipe(String idUser, String idRecipe) {
        Meal meal = mealRepository.findById(idRecipe)
                .orElseThrow(() -> new DataNotFoundException("Recipe Not Founded"));

        userRepository.findById(idUser)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteMeals().remove(meal);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    public void doneRecipe(String idUser, String idRecipe) {
        Meal meal = mealRepository.findById(idRecipe)
                .orElseThrow(() -> new DataNotFoundException("Recipe Not Founded"));

        userRepository.findById(idUser)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getDoneMeals().add(meal);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    public void undoneRecipe(String idUser, String idRecipe) {
        Meal meal = mealRepository.findById(idRecipe)
                .orElseThrow(() -> new DataNotFoundException("Recipe Not Founded"));

        userRepository.findById(idUser)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getDoneMeals().remove(meal);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }
}
