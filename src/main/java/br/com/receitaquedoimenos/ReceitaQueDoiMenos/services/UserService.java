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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A classe UserService fornece métodos para realizar operações relacionadas a usuários, incluindo recuperação de informações,
 * manipulação de refeições e bebidas criadas e favoritas, atualização de perfil e interações de "curtir" e "descurtir" com
 * refeições e bebidas. Interage com os repositórios UserRepository, MealRepository e DrinkRepository para persistência e
 * recuperação de dados de usuários, refeições e bebidas, respectivamente. Além disso, utiliza o UserMapper e os validadores
 * de palavras proibidas (ForbiddenWordsValidator) para mapeamento de dados e validação de informações do usuário.
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @version 1.0
 * @see UserRepository
 * @see MealRepository
 * @see DrinkRepository
 * @see UserMapper
 * @see ForbiddenWordsValidator
 * @since 2023.2
 */
@Service
@Slf4j(topic = "USER_SERVICE")
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

    /**
     * Obtém as informações de um usuário com base no ID do usuário.
     *
     * @param userID O ID do usuário para o qual as informações estão sendo recuperadas.
     * @return As informações do usuário como um UserResponseDTO.
     * @throws DataNotFoundException Se o usuário com o ID especificado não for encontrado.
     */
    @Transactional
    public UserResponseDTO getInfo(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> {
                    log.info("INFORMAÇÕES DO USUÁRIO " + userID + " RETORNADAS COM SUCESSO");
                    return userMapper.toResponseDTO(userFounded);
                })
                .orElseThrow(() -> new DataNotFoundException("User Not Founded"));
    }

    /**
     * Obtém todas as refeições criadas por um usuário com base no ID do usuário.
     *
     * @param userID O ID do usuário para o qual as refeições estão sendo recuperadas.
     * @return Uma lista de refeições criadas pelo usuário como MealResponseDTO.
     * @throws DataNotFoundException Se o usuário com o ID especificado não for encontrado ou não tiver refeições criadas.
     */
    @Transactional
    public List<MealResponseDTO> getAllCreatedMeals(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> {
                            log.info("REFEIÇÕES DO USUÁRIO " + userID + " RETORNADAS COM SUCESSO");
                            return mealRepository
                                    .findAllById(userFounded.getCreatedMealsID())
                                    .parallelStream()
                                    .map(mealMapper::toResponseDTO)
                                    .collect(Collectors.toList());
                        }
                ).orElseThrow(() -> new DataNotFoundException("Data Not Founded"));
    }

    /**
     * Obtém todas as refeições favoritas de um usuário com base no ID do usuário.
     *
     * @param userID O ID do usuário para o qual as refeições favoritas estão sendo recuperadas.
     * @return Uma lista de refeições favoritas do usuário como MealResponseDTO.
     * @throws DataNotFoundException Se o usuário com o ID especificado não for encontrado ou não tiver refeições favoritas.
     */
    @Transactional
    public List<MealResponseDTO> getAllFavoriteMeals(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> {
                            log.info("REFEIÇÕES FAVORITADAS DO USUÁRIO " + userID + " RETORNADAS COM SUCESSO");
                            return mealRepository
                                    .findAllById(userFounded.getFavoriteMealsID())
                                    .parallelStream()
                                    .map(mealMapper::toResponseDTO)
                                    .collect(Collectors.toList());
                        }

                ).orElseThrow(() -> new DataNotFoundException("Data Not Founded"));
    }

    /**
     * Obtém todas as bebidas criadas por um usuário com base no ID do usuário.
     *
     * @param userID O ID do usuário para o qual as bebidas estão sendo recuperadas.
     * @return Uma lista de bebidas criadas pelo usuário como DrinkResponseDTO.
     * @throws DataNotFoundException Se o usuário com o ID especificado não for encontrado ou não tiver bebidas criadas.
     */
    @Transactional
    public List<DrinkResponseDTO> getAllCreatedDrinks(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> {
                            log.info("DRINKS CRIADOS DO USUÁRIO " + userID + " RETORNADAS COM SUCESSO");
                            return drinkRepository
                                    .findAllById(userFounded.getCreatedDrinksID())
                                    .parallelStream()
                                    .map(drinkMapper::toResponseDTO)
                                    .collect(Collectors.toList());
                        }

                ).orElseThrow(() -> new DataNotFoundException("Data Not Founded"));
    }

    /**
     * Obtém todas as bebidas favoritas de um usuário com base no ID do usuário.
     *
     * @param userID O ID do usuário para o qual as bebidas favoritas estão sendo recuperadas.
     * @return Uma lista de bebidas favoritas do usuário como DrinkResponseDTO.
     * @throws DataNotFoundException Se o usuário com o ID especificado não for encontrado ou não tiver bebidas favoritas.
     */
    @Transactional
    public List<DrinkResponseDTO> getAllFavoriteDrinks(String userID) {
        return userRepository.findById(userID)
                .map(userFounded -> {
                            log.info("DRINKS CRIADOS DO USUÁRIO " + userID + " RETORNADAS COM SUCESSO");
                            return drinkRepository.
                                    findAllById(userFounded.getFavoriteDrinksID())
                                    .parallelStream()
                                    .map(drinkMapper::toResponseDTO)
                                    .collect(Collectors.toList());
                        }
                ).orElseThrow(() -> new DataNotFoundException("Data Not Founded"));
    }

    /**
     * Atualiza as informações do perfil de um usuário com base no ID do usuário e nas informações fornecidas.
     *
     * @param userID         O ID do usuário cujo perfil está sendo atualizado.
     * @param userRequestDTO O DTO contendo as informações atualizadas do usuário.
     * @return As informações do usuário atualizadas como um UserResponseDTO.
     * @throws ConflictDataException Se já existir um usuário com o mesmo e-mail no sistema.
     * @throws DataNotFoundException Se o usuário com o ID especificado não for encontrado.
     */
    @Transactional
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
                    log.info("PERFIL DO USUÁRIO " + userID + " ATUALIZADAS COM SUCESSO");

                    return userMapper.toResponseDTO(userRepository.save(userFounded));
                })
                .orElseThrow(() -> new DataNotFoundException("User Not Founded"));
    }

    /**
     * Exclui a conta de um usuário com base no ID do usuário, juntamente com todas as suas refeições associadas
     *
     * @param userID O ID do usuário cuja conta está sendo excluída.
     * @throws DataNotFoundException Se o usuário com o ID especificado não for encontrado.
     */
    @Transactional
    public void deleteAccount(String userID) {
        userRepository.findById(userID)
                .ifPresentOrElse(user -> {
                    mealRepository.deleteAllById(user.getCreatedMealsID());
                    drinkRepository.deleteAllById(user.getCreatedDrinksID());
                    userRepository.delete(user);
                    log.info("PERFIL DO USUÁRIO " + userID + " DELETADO COM SUCESSO");
                }, () -> new DataNotFoundException("User Not Founded"));
    }

    /**
     * Adiciona uma refeição à lista de refeições favoritas de um usuário.
     *
     * @param userID O ID do usuário que está adicionando a refeição aos favoritos.
     * @param mealID O ID da refeição que está sendo adicionada aos favoritos.
     * @throws UnauthorizedOperationException Se o usuário estiver tentando curtir sua própria refeição.
     * @throws DataNotFoundException          Se o usuário ou a refeição não for encontrado.
     */
    @Transactional
    public void likeMeal(String userID, String mealID) {
        Meal meal = mealRepository.findById(mealID)
                .orElseThrow(() -> new DataNotFoundException("Meal Not Founded"));

        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    if (meal.getCreatorID().equals(userID))
                        throw new UnauthorizedOperationException("Unauthorized Like Operation");

                    userFounded.getFavoriteMealsID().add(mealID);
                    userRepository.save(userFounded);
                    log.info("USUÁRIO " + userID + "LIKE REFEIÇÃO " + mealID);
                }, () -> new DataNotFoundException("User Not Founded"));
    }

    /**
     * Remove uma refeição da lista de refeições favoritas de um usuário.
     *
     * @param userID O ID do usuário que está removendo a refeição dos favoritos.
     * @param mealID O ID da refeição que está sendo removida dos favoritos.
     * @throws DataNotFoundException Se o usuário ou a refeição não for encontrado.
     */
    @Transactional
    public void unlikeMeal(String userID, String mealID) {
        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteMealsID().remove(mealID);
                    userRepository.save(userFounded);
                    log.info("USUÁRIO " + userID + "UNLIKE REFEIÇÃO " + mealID);

                }, () -> new DataNotFoundException("User Not Founded"));
    }


    /**
     * Adiciona uma bebida à lista de bebidas favoritas de um usuário.
     *
     * @param userID  O ID do usuário que está adicionando a bebida aos favoritos.
     * @param drinkID O ID da bebida que está sendo adicionada aos favoritos.
     * @throws UnauthorizedOperationException Se o usuário estiver tentando curtir sua própria bebida.
     * @throws DataNotFoundException          Se o usuário ou a bebida não for encontrado.
     */
    @Transactional
    public void likeDrink(String userID, String drinkID) {
        Drink drink = drinkRepository.findById(drinkID)
                .orElseThrow(() -> new DataNotFoundException("Drink Not Founded"));

        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    if (drink.getCreatorID().equals(userID))
                        throw new UnauthorizedOperationException("Unauthorized Like Operation");

                    userFounded.getFavoriteDrinksID().add(drinkID);
                    userRepository.save(userFounded);
                    log.info("USUÁRIO " + userID + "LIKE REFEIÇÃO " + drinkID);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

    /**
     * Remove uma bebida da lista de bebidas favoritas de um usuário.
     *
     * @param userID  O ID do usuário que está removendo a bebida dos favoritos.
     * @param drinkID O ID da bebida que está sendo removida dos favoritos.
     * @throws DataNotFoundException Se o usuário ou a bebida não for encontrado.
     */
    @Transactional
    public void unlikeDrink(String userID, String drinkID) {
        userRepository.findById(userID)
                .ifPresentOrElse(userFounded -> {
                    userFounded.getFavoriteDrinksID().remove(drinkID);
                    userRepository.save(userFounded);
                    log.info("USUÁRIO " + userID + "UNLIKE DRINK " + drinkID);

                }, () -> new DataNotFoundException("User Not Founded"));
    }

}
