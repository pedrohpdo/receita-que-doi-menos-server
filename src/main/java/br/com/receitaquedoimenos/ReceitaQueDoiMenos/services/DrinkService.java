package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.UnauthorizedOperationException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.Meal;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.DrinkRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils.ForbiddenWordsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrinkService {

    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    DrinkMapper drinkMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ForbiddenWordsValidator wordValidator;

    public DrinkResponseDTO createDrink(DrinkRequestDTO drinkRequestDTO) {
        wordValidator.validateDrink(drinkRequestDTO);

        Drink newDrink = drinkRepository.save(drinkMapper.toEntity(drinkRequestDTO));

        userRepository.findById(drinkRequestDTO.creatorID())
                .ifPresentOrElse(user -> {
                    user.getCreatedDrinks().add(newDrink);
                    userRepository.save(user);

                }, () -> new DataNotFoundException("User Not Founded"));
        return drinkMapper.toResponseDTO(drinkRepository.save(drinkMapper.toEntity(drinkRequestDTO)));
    }

    public List<DrinkResponseDTO> getDrinksByName(String drinkName) {
        return drinkRepository.findAllByNameIgnoreCase(drinkName)
                .stream()
                .map(drinkMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public List<DrinkResponseDTO> getDrinksByType(TypeDrink type) {
        return drinkRepository.findAllByTypeDrink(type)
                .stream()
                .map(drinkMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DrinkResponseDTO> getAllDrinks() {
        return drinkRepository.findAll()
                .stream()
                .map(drinkMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DrinkResponseDTO getDrinkById(String drinkID) {
        return drinkRepository.findById(drinkID)
                .map(drinkInfo -> drinkMapper.toResponseDTO(drinkInfo))
                .orElseThrow(() -> new DataNotFoundException("Drink Not Found"));
    }

    public DrinkResponseDTO updateDrink(String drinkID, String userID, DrinkRequestDTO drinkRequestDTO) {
        return drinkRepository.findById(drinkID)
                .map(drinkFounded -> {
                    if (drinkFounded.getCreatorID().equals(userID)) {
                        throw new UnauthorizedOperationException("Unauthorized Delete Operation");
                    }
                    wordValidator.validateDrink(drinkRequestDTO);

                    drinkFounded.setName(drinkRequestDTO.name());
                    drinkFounded.setTypeDrink(drinkRequestDTO.typeDrink());
                    drinkFounded.setPhotoURL(drinkRequestDTO.photoURL());
                    drinkFounded.setVideoURL(drinkRequestDTO.videoURL());
                    drinkFounded.setInstructions(drinkRequestDTO.instructions());
                    drinkFounded.setIngredients(drinkRequestDTO.ingredients());

                    Drink drinkUpdated = drinkRepository.save(drinkFounded);

                    //Cascade Operation on UserCreator
                    userRepository.findById(userID)
                            .ifPresent(userCreator -> {
                                for (Drink drinkToUpdate: userCreator.getCreatedDrinks()) {
                                    if (drinkToUpdate.getId().equals(drinkID)) {
                                        drinkToUpdate.setName(drinkRequestDTO.name());
                                        drinkToUpdate.setTypeDrink(drinkRequestDTO.typeDrink());
                                        drinkToUpdate.setPhotoURL(drinkRequestDTO.photoURL());
                                        drinkToUpdate.setVideoURL(drinkRequestDTO.videoURL());
                                        drinkToUpdate.setInstructions(drinkRequestDTO.instructions());
                                        drinkToUpdate.setIngredients(drinkRequestDTO.ingredients());
                                        userRepository.save(userCreator);
                                        break;
                                    }
                                }
                            });

                    // Cascade Operation on All Other Users
                    for (User userToUpdate: userRepository.findByIdNot(userID)) {
                        for(Drink drinkToUpdate : userToUpdate.getFavoriteDrinks()){
                            if (drinkToUpdate.getId().equals(drinkID)) {
                                drinkToUpdate.setName(drinkRequestDTO.name());
                                drinkToUpdate.setTypeDrink(drinkRequestDTO.typeDrink());
                                drinkToUpdate.setPhotoURL(drinkRequestDTO.photoURL());
                                drinkToUpdate.setVideoURL(drinkRequestDTO.videoURL());
                                drinkToUpdate.setInstructions(drinkRequestDTO.instructions());
                                drinkToUpdate.setIngredients(drinkRequestDTO.ingredients());
                                userRepository.save(userToUpdate);
                                break;
                            }
                        }
                    }


                    return drinkMapper.toResponseDTO(drinkUpdated);
                }).orElseThrow(() -> new DataNotFoundException("Drink Not Found"));
    }

    public void deleteDrink(String drinkID, String userID) {
        drinkRepository.findById(drinkID)
                .ifPresentOrElse(drinkFounded -> {
                        if (!drinkFounded.getCreatorID().equals(userID)) {
                            throw new UnauthorizedOperationException("Unauthorized Delete Operation");
                        }
                        drinkRepository.delete(drinkFounded);

                        //Cascade Operation on UserCreator
                        userRepository.findById(userID)
                                .ifPresent(userCreator -> {
                                    for (Drink drinkToDelete: userCreator.getCreatedDrinks()) {
                                        if (drinkToDelete.getId().equals(drinkID)) {
                                            userCreator.getCreatedDrinks().remove(drinkToDelete);
                                            userRepository.save(userCreator);
                                            break;
                                        }
                                    }
                                });

                        // Cascade Operations
                        for (User userToUpdate: userRepository.findByIdNot(userID)) {
                            for (Drink checkedDrinks: userToUpdate.getFavoriteDrinks()) {
                                if (checkedDrinks.getId().equals(drinkID)){
                                    userToUpdate.getFavoriteDrinks().remove(checkedDrinks);
                                    userRepository.save(userToUpdate);
                                    break;
                                }
                            }
                        }

                    },
                    () -> new DataNotFoundException("Drink Not Found"));
    }
}
