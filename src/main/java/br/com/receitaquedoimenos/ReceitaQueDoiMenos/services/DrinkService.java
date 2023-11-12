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

/**
 * A classe DrinkService fornece métodos para gerenciar operações relacionadas a bebidas, incluindo criação,
 * recuperação, atualização e exclusão de bebidas. Ela interage com o DrinkRepository e o UserRepository
 * para lidar com a persistência e recuperação de dados de bebidas e usuários. Além disso, ela utiliza o
 * ForbiddenWordsValidator para garantir que as bebidas sigam certas restrições de conteúdo.
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @version 1.0
 * @see DrinkRepository
 * @see UserRepository
 * @see ForbiddenWordsValidator
 * @since 2023.2
 */
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

    /**
     * Cria uma nova bebida com base no DrinkRequestDTO fornecido, valida seu conteúdo e a associa ao
     * usuário correspondente que a criou. Retorna a bebida criada como um DrinkResponseDTO.
     *
     * @param drinkRequestDTO O DTO contendo informações para criar a bebida.
     * @return A bebida criada como um DrinkResponseDTO.
     * @throws DataNotFoundException Se o usuário criador não for encontrado.
     */
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

    /**
     * Recupera uma lista de bebidas com o nome especificado.
     *
     * @param drinkName O nome das bebidas a serem recuperadas.
     * @return Uma lista de bebidas que correspondem ao nome especificado.
     */
    public List<DrinkResponseDTO> getDrinksByName(String drinkName) {
        return drinkRepository.findAllByNameIgnoreCase(drinkName)
                .parallelStream()
                .map(drinkMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma lista de bebidas do tipo especificado.
     *
     * @param type O tipo de bebidas a serem recuperadas.
     * @return Uma lista de bebidas que correspondem ao tipo especificado.
     */
    public List<DrinkResponseDTO> getDrinksByType(TypeDrink type) {
        return drinkRepository.findAllByTypeDrink(type)
                .parallelStream()
                .map(drinkMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma lista de todas as bebidas no sistema.
     *
     * @return Uma lista contendo todas as bebidas no sistema.
     */
    public List<DrinkResponseDTO> getAllDrinks() {
        return drinkRepository.findAll()
                .parallelStream()
                .map(drinkMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera informações sobre uma bebida específica com base em seu ID.
     *
     * @param drinkID O ID da bebida a ser recuperada.
     * @return Informações sobre a bebida especificada como um DrinkResponseDTO.
     * @throws DataNotFoundException Se a bebida com o ID especificado não for encontrada.
     */
    public DrinkResponseDTO getDrinkById(String drinkID) {
        return drinkRepository.findById(drinkID)
                .map(drinkInfo -> drinkMapper.toResponseDTO(drinkInfo))
                .orElseThrow(() -> new DataNotFoundException("Drink Not Found"));
    }

    /**
     * Atualiza as informações de uma bebida específica, validando o conteúdo e garantindo que o
     * usuário solicitante tenha a autorização necessária. Retorna a bebida atualizada como um DrinkResponseDTO.
     *
     * @param drinkID         O ID da bebida a ser atualizada.
     * @param userID          O ID do usuário que está fazendo a solicitação de atualização.
     * @param drinkRequestDTO O DTO contendo as informações atualizadas para a bebida.
     * @return A bebida atualizada como um DrinkResponseDTO.
     * @throws UnauthorizedOperationException Se a operação de atualização não for autorizada para o usuário solicitante.
     * @throws DataNotFoundException          Se a bebida com o ID especificado não for encontrada.
     */
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

    /**
     * Exclui uma bebida específica, garantindo que o usuário solicitante tenha a autorização necessária.
     * <p>
     * Após a exclusão, uma operação em cascada é executada, apagando o ID do drinks em outras possíveis collections.
     *
     * @param drinkID O ID da bebida a ser excluída.
     * @param userID  O ID do usuário que está fazendo a solicitação de exclusão.
     * @throws UnauthorizedOperationException Se a operação de exclusão não for autorizada para o usuário solicitante.
     * @throws DataNotFoundException          Se a bebida com o ID especificado não for encontrada.
     */
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
