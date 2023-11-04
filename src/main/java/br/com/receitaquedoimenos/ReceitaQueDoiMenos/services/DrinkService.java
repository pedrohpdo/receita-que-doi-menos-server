package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.UnauthorizedOperationException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.DrinkRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils.ForbiddenWordsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                    user.getCreatedDrinksID().add(newDrink.getId());
                    userRepository.save(user);

                }, () -> new DataNotFoundException("User Not Founded"));

        return drinkMapper.toResponseDTO(newDrink);
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
                    if (!drinkFounded.getCreatorID().equals(userID))
                        throw new UnauthorizedOperationException("Unauthorized Delete Operation");

                    wordValidator.validateDrink(drinkRequestDTO);

                    drinkFounded.setName(drinkRequestDTO.name());
                    drinkFounded.setTypeDrink(drinkRequestDTO.typeDrink());
                    drinkFounded.setPhotoURL(drinkRequestDTO.photoURL());
                    drinkFounded.setVideoURL(drinkRequestDTO.videoURL());
                    drinkFounded.setInstructions(drinkRequestDTO.instructions());
                    drinkFounded.setIngredients(drinkRequestDTO.ingredients());

                    return drinkMapper.toResponseDTO(drinkRepository.save(drinkFounded));

                }).orElseThrow(() -> new DataNotFoundException("Drink Not Found"));
    }

    public void deleteDrink(String drinkID, String userID) {
        drinkRepository.findById(drinkID)
                .ifPresentOrElse(drinkFounded -> {
                            if (!drinkFounded.getCreatorID().equals(userID))
                                throw new UnauthorizedOperationException("Unauthorized Delete Operation");

                            drinkRepository.delete(drinkFounded);

                            //Cascade Operation on UserCreator
                            userRepository.findById(userID)
                                    .ifPresent(userCreator -> {
                                        userCreator.getCreatedDrinksID().remove(drinkID);
                                        userRepository.save(userCreator);
                                    });

                            // Cascade Operations
                            for (User userToUpdate : userRepository.findByFavoriteDrinksIDContainingAndIdNot(drinkID, userID)) {
                                userToUpdate.getFavoriteDrinksID().remove(drinkID);
                                userRepository.save(userToUpdate);
                            }

                        }, () -> new DataNotFoundException("Drink Not Found"));
    }
}
