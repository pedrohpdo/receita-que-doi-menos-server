package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.ConflictDataException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.UnauthorizedOperationException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.Drink;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkMapper;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.Meal;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealMapper;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.DrinkRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.MealRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils.ForbiddenWordsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MealMapper mealMapper;

    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    DrinkMapper drinkMapper;


    @Autowired
    UserMapper userMapper;

    @Autowired
    ForbiddenWordsValidator wordValidator;

    public UserResponseDTO getInfo(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> userMapper.toResponseDTO(userFounded))
                .orElseThrow(() -> new DataNotFoundException("User Not Founded"));
    }

    public List<MealResponseDTO> getAllCreatedMeals(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> mealRepository
                        .findAllById(userFounded.getCreatedMealsID())
                        .parallelStream()
                        .map(mealMapper::toResponseDTO)
                        .collect(Collectors.toList())
                ).orElseThrow(() -> new DataNotFoundException("Data Not Founded"));
    }

    public List<MealResponseDTO> getAllFavoriteMeals(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> mealRepository
                        .findAllById(userFounded.getFavoriteMealsID())
                        .parallelStream()
                        .map(mealMapper::toResponseDTO)
                        .collect(Collectors.toList())

                ).orElseThrow(() -> new DataNotFoundException("Data Not Founded"));
    }

    public List<DrinkResponseDTO> getAllCreatedDrinks(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> drinkRepository
                        .findAllById(userFounded.getCreatedDrinksID())
                        .parallelStream()
                        .map(drinkMapper::toResponseDTO)
                        .collect(Collectors.toList())

                ).orElseThrow(() -> new DataNotFoundException("Data Not Founded"));
    }

    public List<DrinkResponseDTO> getAllFavoriteDrinks(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> drinkRepository.
                        findAllById(userFounded.getFavoriteDrinksID())
                        .parallelStream()
                        .map(drinkMapper::toResponseDTO)
                        .collect(Collectors.toList())

                ).orElseThrow(() -> new DataNotFoundException("Data Not Founded"));
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
                    mealRepository.deleteAllById(user.getCreatedMealsID());
                    drinkRepository.deleteAllById(user.getCreatedDrinksID());
                    userRepository.delete(user);

                }, () -> new DataNotFoundException("User Not Founded"));
    }


    public void likeMeal(String userID, String mealID) {
        Meal meal = mealRepository.findById(mealID)
                .orElseThrow(() -> new DataNotFoundException("Meal Not Founded"));

        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    if(meal.getCreatorID().equals(userID)) throw new UnauthorizedOperationException("Unauthorized Like Operation");

                    userFounded.getFavoriteMealsID().add(mealID);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    public void unlikeMeal(String userID, String mealID) {
        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteMealsID().remove(mealID);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    public void likeDrink(String userID, String drinkID) {
        Drink drink = drinkRepository.findById(drinkID)
                .orElseThrow(() -> new DataNotFoundException("Drink Not Founded"));

        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    if(drink.getCreatorID().equals(userID)) throw new UnauthorizedOperationException("Unauthorized Like Operation");

                    userFounded.getFavoriteDrinksID().add(drinkID);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    public void unlikeDrink(String userID, String drinkID) {
        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteDrinksID().remove(drinkID);
                    userRepository.save(userFounded);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

}
