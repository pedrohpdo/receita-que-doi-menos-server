package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.EmptyIngredientException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.UnauthorizedOperationException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.MealRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils.ForbiddenWordsValidator;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A classe MealService fornece métodos para gerenciar operações relacionadas a refeições, incluindo criação,
 * recuperação, atualização e exclusão de refeições. Ela interage com o MealRepository e o UserRepository
 * para lidar com a persistência e recuperação de dados de refeições e usuários. Além disso, ela utiliza o
 * ForbiddenWordsValidator para garantir que as refeições sigam certas restrições de conteúdo.
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @version 1.0
 * @see MealRepository
 * @see UserRepository
 * @see ForbiddenWordsValidator
 * @since 2023.2
 */
@Service
@Slf4j(topic = "RECIPE SERVICE")
public class MealService {

    @Autowired
    MealRepository mealRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MealMapper mealMapper;

    @Autowired
    ForbiddenWordsValidator wordValidator;

    /**
     * Cria uma nova refeição com base no MealRequestDTO fornecido, valida seu conteúdo e a associa ao
     * usuário correspondente que a criou. Retorna a refeição criada como um MealResponseDTO.
     *
     * @param mealRequestDTO O DTO contendo informações para criar a refeição.
     * @return A refeição criada como um MealResponseDTO.
     * @throws DataNotFoundException Se o usuário criador não for encontrado.
     */
    @Transactional
    public MealResponseDTO createMeal(MealRequestDTO mealRequestDTO) {

        mealRequestDTO.ingredients().forEach(ingredient -> {
            if (ingredient.isBlank()) { throw new EmptyIngredientException("Ingredient Must Not Be Blank");}
        });

        wordValidator.validateMeal(mealRequestDTO);
        Meal newMeal = mealRepository.save(mealMapper.toEntity(mealRequestDTO));

        userRepository.findById(mealRequestDTO.creatorID())
                .ifPresentOrElse(user -> {
                    user.getCreatedMealsID().add(newMeal.getId());
                    userRepository.save(user);

                    log.info(LogInfo.RECIPE_CREATED(user.getEmail(), mealRequestDTO.name()));

                }, () -> new DataNotFoundException("User Not Found"));


        return mealMapper.toResponseDTO(newMeal);
    }

    /**
     * Recupera uma lista de todas as refeições no sistema.
     *
     * @return Uma lista contendo todas as refeições no sistema.
     */
    @Transactional
    public List<MealResponseDTO> getAll() {
        log.info("TODAS AS RECEITAS RETORNADAS");
        return mealRepository.findAll()
                .stream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma lista de refeições com o nome especificado.
     *
     * @param name O nome das refeições a serem recuperadas.
     * @return Uma lista de refeições que correspondem ao nome especificado.
     */
    @Transactional
    public List<MealResponseDTO> getMealsByName(String name) {
        log.info("RECEITAS RETORNADAS PELO NOME");
        return mealRepository.findAllByNameIgnoreCase(name)
                .stream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera informações sobre uma refeição específica com base em seu ID.
     *
     * @param mealID O ID da refeição a ser recuperada.
     * @return Informações sobre a refeição especificada como um MealResponseDTO.
     * @throws DataNotFoundException Se a refeição com o ID especificado não for encontrada.
     */
    @Transactional
    public MealResponseDTO getMealsById(String mealID) {
        log.info("RECEITAS RETORNADAS PELO SEU ID");
        return mealRepository.findById(mealID)
                .map(meal -> mealMapper.toResponseDTO(meal))
                .orElseThrow(() -> new DataNotFoundException("Data no Founded"));
    }

    /**
     * Recupera uma lista de refeições do tipo especificado.
     *
     * @param typeMeal O tipo de refeições a serem recuperadas.
     * @return Uma lista de refeições que correspondem ao tipo especificado.
     */
    @Transactional
    public List<MealResponseDTO> getMealsByTypeMeal(TypeMeal typeMeal) {
        log.info("RECEITAS RETORNADAS PELO SEU TIPO");
        return mealRepository.findAllByTypeMeal(typeMeal)
                .stream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza as informações de uma refeição específica, validando o conteúdo e garantindo que o
     * usuário solicitante tenha a autorização necessária. Retorna a refeição atualizada como um MealResponseDTO.
     *
     * @param mealID         O ID da refeição a ser atualizada.
     * @param userID         O ID do usuário que está fazendo a solicitação de atualização.
     * @param mealRequestDTO O DTO contendo as informações atualizadas para a refeição.
     * @return A refeição atualizada como um MealResponseDTO.
     * @throws UnauthorizedOperationException Se a operação de atualização não for autorizada para o usuário solicitante.
     * @throws DataNotFoundException          Se a refeição com o ID especificado não for encontrada.
     */
    @Transactional
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
                    log.info("RECEITAS ATUALIZADAS COM SUCESSO");

                    return mealMapper.toResponseDTO(mealUpdated);

                }).orElseThrow(() -> new DataNotFoundException("Recipe Not Found"));
    }


    /**
     * Exclui uma refeição específica, garantindo que o usuário solicitante tenha a autorização necessária.
     *
     * @param mealID O ID da refeição a ser excluída.
     * @param userID O ID do usuário que está fazendo a solicitação de exclusão.
     * @throws UnauthorizedOperationException Se a operação de exclusão não for autorizada para o usuário solicitante.
     * @throws DataNotFoundException          Se a refeição com o ID especificado não for encontrada.
     */
    @Transactional
    public void deleteMeal(String mealID, String userID) {
        mealRepository.findById(mealID)
                .ifPresentOrElse(meal -> {
                    if (!meal.getCreatorID().equals(userID))
                        throw new UnauthorizedOperationException("Unauthorized Operation");

                    mealRepository.delete(meal);

                    //Cascade Operation on UserCreator
                    userRepository.findById(userID)
                            .ifPresent(userCreator -> {
                                userCreator.getCreatedMealsID().remove(mealID);
                                userRepository.save(userCreator);
                                log.info(LogInfo.RECIPE_DELETED(userCreator.getEmail(), meal.getName()));
                            });
                    // Cascade Operations
                    for (User userToUpdate : userRepository.findByFavoriteMealsIDContainingAndIdNot(mealID, userID)) {
                        userToUpdate.getFavoriteMealsID().remove(mealID);
                        userRepository.save(userToUpdate);
                    }
                }, () -> new DataNotFoundException("Recipe Not Found"));
    }
}
