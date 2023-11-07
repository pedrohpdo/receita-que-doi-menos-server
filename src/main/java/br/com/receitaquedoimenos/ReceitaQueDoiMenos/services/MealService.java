package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.UnauthorizedOperationException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.MealRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils.ForbiddenWordsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    @Autowired
    MealRepository mealRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MealMapper mealMapper;

    @Autowired
    ForbiddenWordsValidator wordValidator;

    public MealResponseDTO createMeal(MealRequestDTO mealRequestDTO) {
        wordValidator.validateMeal(mealRequestDTO);
        Meal newMeal = mealRepository.save(mealMapper.toEntity(mealRequestDTO));

        userRepository.findById(mealRequestDTO.creatorID())
                .ifPresentOrElse(user -> {
                    user.getCreatedMealsID().add(newMeal.getId());
                    userRepository.save(user);

                }, () -> new DataNotFoundException("User Not Found"));

        return mealMapper.toResponseDTO(newMeal);
    }

    public List<MealResponseDTO> getAll() {
        return mealRepository.findAll()
                .parallelStream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MealResponseDTO> getMealsByName(String name) {
        return mealRepository.findAllByNameIgnoreCase(name)
                .parallelStream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MealResponseDTO getMealsById(String mealID) {
        return mealRepository.findById(mealID)
                .map(meal -> mealMapper.toResponseDTO(meal))
                .orElseThrow(() -> new DataNotFoundException("Data no Founded"));
    }

    public List<MealResponseDTO> getMealsByTypeMeal(TypeMeal typeMeal) {
        return mealRepository.findAllByTypeMeal(typeMeal)
                .parallelStream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MealResponseDTO updateMealInfo(String mealID, String userID, MealRequestDTO mealRequestDTO) {
        return mealRepository.findById(mealID)
                .map(mealFounded -> {
                    if (!mealFounded.getCreatorID().equals(userID)) throw new UnauthorizedOperationException("Unauthorized Update Operation");

                    wordValidator.validateMeal(mealRequestDTO);

                    mealFounded.setName(mealRequestDTO.name());
                    mealFounded.setTypeMeal(mealRequestDTO.typeMeal());
                    mealFounded.setPhotoURL(mealRequestDTO.photoURL());
                    mealFounded.setVideoURL(mealRequestDTO.videoURL());
                    mealFounded.setInstructions(mealRequestDTO.instructions());
                    mealFounded.setIngredients(mealRequestDTO.ingredients());

                    Meal mealUpdated = mealRepository.save(mealFounded);

                    return mealMapper.toResponseDTO(mealUpdated);

                }).orElseThrow(() -> new DataNotFoundException("Recipe Not Found"));
    }


    public void deleteMeal(String mealID, String userID) {
        mealRepository.findById(mealID)
                .ifPresentOrElse(meal -> {
                    if (!meal.getCreatorID().equals(userID)) throw new UnauthorizedOperationException("Unauthorized Operation");

                    mealRepository.delete(meal);

                    //Cascade Operation on UserCreator
                    userRepository.findById(userID)
                            .ifPresent(userCreator -> {
                                userCreator.getCreatedMealsID().remove(mealID);
                                userRepository.save(userCreator);
                            });

                    // Cascade Operations
                    for (User userToUpdate: userRepository.findByFavoriteMealsIDContainingAndIdNot(mealID, userID)) {
                                userToUpdate.getFavoriteMealsID().remove(mealID);
                                userRepository.save(userToUpdate);
                    }
                }, () -> new DataNotFoundException("Recipe Not Found"));
    }
}
