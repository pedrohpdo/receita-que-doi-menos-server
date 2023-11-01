package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.UnauthorizedOperationException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.MealRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils.ForbiddenWordsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
                    user.getCreatedMeals().add(newMeal);
                    userRepository.save(user);

                }, () -> new DataNotFoundException("User Not Found"));

        return mealMapper.toResponseDTO(newMeal);
    }

    public List<MealResponseDTO> getAll() {
        return mealRepository.findAll()
                .stream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MealResponseDTO> getMealsByName(String name) {
        return mealRepository.findAllByNameIgnoreCase(name)
                .stream()
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
                .stream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MealResponseDTO updateMealInfo(String mealID, String userID, MealRequestDTO mealRequestDTO) {
        return mealRepository.findById(mealID)
                .map(mealFounded -> {
                    if (!mealFounded.getCreatorID().equals(userID)) {
                        throw new UnauthorizedOperationException("Unauthorized Update Operation");
                    }

                    wordValidator.validateMeal(mealRequestDTO);

                    mealFounded.setName(mealRequestDTO.name());
                    mealFounded.setTypeMeal(mealRequestDTO.typeMeal());
                    mealFounded.setPhotoURL(mealRequestDTO.photoURL());
                    mealFounded.setVideoURL(mealRequestDTO.videoURL());
                    mealFounded.setInstructions(mealRequestDTO.instructions());
                    mealFounded.setIngredients(mealRequestDTO.ingredients());

                    Meal mealUpdated = mealRepository.save(mealFounded);

                    //Cascade Operation on UserCreator
                    userRepository.findById(userID)
                            .ifPresent(userCreator -> {
                                for (Meal mealToUpdate: userCreator.getCreatedMeals()) {
                                    if (mealToUpdate.getId().equals(mealID)) {
                                        mealToUpdate.setName(mealRequestDTO.name());
                                        mealToUpdate.setTypeMeal(mealRequestDTO.typeMeal());
                                        mealToUpdate.setPhotoURL(mealRequestDTO.photoURL());
                                        mealToUpdate.setVideoURL(mealRequestDTO.videoURL());
                                        mealToUpdate.setInstructions(mealRequestDTO.instructions());
                                        mealToUpdate.setIngredients(mealRequestDTO.ingredients());
                                        userRepository.save(userCreator);
                                        break;
                                    }
                                }
                            });

                    // Cascade Operation on All Other Users
                    for (User userToUpdate: userRepository.findByIdNot(userID)) {
                        for(Meal mealToUpdate : userToUpdate.getFavoriteMeals()){
                            if (mealToUpdate.getId().equals(mealID)) {
                                mealToUpdate.setName(mealRequestDTO.name());
                                mealToUpdate.setTypeMeal(mealRequestDTO.typeMeal());
                                mealToUpdate.setPhotoURL(mealRequestDTO.photoURL());
                                mealToUpdate.setVideoURL(mealRequestDTO.videoURL());
                                mealToUpdate.setInstructions(mealRequestDTO.instructions());
                                mealToUpdate.setIngredients(mealRequestDTO.ingredients());
                                userRepository.save(userToUpdate);
                                break;
                            }
                        }
                    }
                    return mealMapper.toResponseDTO(mealUpdated);

                }).orElseThrow(() -> new DataNotFoundException("Recipe Not Found"));
    }


    public void deleteMeal(String mealID, String userID) {
        mealRepository.findById(mealID)
                .ifPresentOrElse(meal -> {
                    if (!meal.getCreatorID().equals(userID)) {
                        throw new UnauthorizedOperationException("Unauthorized Operation");
                    }
                    mealRepository.delete(meal);

                    //Cascade Operation on UserCreator
                    userRepository.findById(userID)
                            .ifPresent(userCreator -> {
                                for (Meal mealToDelete: userCreator.getCreatedMeals()) {
                                    if (mealToDelete.getId().equals(mealID)) {
                                        userCreator.getCreatedMeals().remove(mealToDelete);
                                        userRepository.save(userCreator);
                                        break;
                                    }
                                }
                            });

                    // Cascade Operations
                    for (User userToUpdate: userRepository.findByIdNot(userID)) {
                        for (Meal checkedMeals: userToUpdate.getFavoriteMeals()) {
                            if (checkedMeals.getId().equals(mealID)){
                                userToUpdate.getFavoriteMeals().remove(checkedMeals);
                                userRepository.save(userToUpdate);
                                break;
                            }
                        }
                    }
                }, () -> new DataNotFoundException("Recipe Not Found"));
    }
}
