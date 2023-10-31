package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.ConflictDataException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.Drink;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.Meal;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.DrinkRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.MealRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils.ForbiddenWordsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ForbiddenWordsValidator wordValidator;

    public UserResponseDTO getInfo(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> userMapper.toResponseDTO(userFounded))
                .orElseThrow(() -> new DataNotFoundException("User Not Founded"));
    }

    public UserResponseDTO updateProfileInfo(String userID, UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmailAndIdNot(userRequestDTO.email(), userID)) {
            throw new ConflictDataException("Already Exists An Email on System.");
        }

        return userRepository.findById(userID)
                .map(userFounded -> {
                    wordValidator.validateUserData(userRequestDTO);

                    userFounded.setPassword(userFounded.getPassword());
                    userFounded.setName(userRequestDTO.name());
                    userFounded.setEmail(userRequestDTO.email());

                    return userMapper.toResponseDTO(userRepository.save(userFounded));
                })
                .orElseThrow(() -> new DataNotFoundException("User Not Founded"));
    }

    public void deleteAccount(String userID) {
        userRepository.findById(userID)
                .ifPresentOrElse(user -> {
                    mealRepository.deleteAll(user.getCreatedMeals());
                    drinkRepository.deleteAll(user.getCreatedDrinks());

                    userRepository.delete(user);
                }, () -> new DataNotFoundException("User Not Founded"));
    }


    public void likeMeal(String userID, String mealID) {
        Meal meal = mealRepository.findById(mealID)
                .orElseThrow(() -> new DataNotFoundException("Meal Not Founded"));

        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteMeals().add(meal);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    public void unlikeMeal(String userID, String mealID) {
        Meal meal = mealRepository.findById(mealID)
                .orElseThrow(() -> new DataNotFoundException("Meal Not Founded"));

        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteMeals().remove(meal);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    public void likeDrink(String userID, String drinkID) {
        Drink drink = drinkRepository.findById(drinkID)
                .orElseThrow(() -> new DataNotFoundException("Drink Not Founded"));

        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteDrinks().add(drink);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    public void unlikeDrink(String userID, String drinkID) {
        Drink drink = drinkRepository.findById(drinkID)
                .orElseThrow(() -> new DataNotFoundException("Drink Not Founded"));

        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteDrinks().remove(drink);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

}
