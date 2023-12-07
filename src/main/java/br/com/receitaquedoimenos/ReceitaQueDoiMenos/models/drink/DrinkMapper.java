package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserCreatorRecipeResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe que Representa um mapeador de entidades, melhorando a abstração dentro dos serviços.
 *
 * @author Pedro Henrique Pereira de Oliveira
 */
@Component
public class DrinkMapper {

    @Autowired
    UserRepository userRepository;

    /**
     * Retorna uma instância da Drink.class a partir de um record DrinkRequestDTO
     *
     * @param drinkRequestDTO DrinkRequestDTO recebido
     * @return Instância Drink.class
     */
    public Drink toEntity(DrinkRequestDTO drinkRequestDTO) {
        return new Drink(
                drinkRequestDTO.name(),
                drinkRequestDTO.typeDrink(),
                drinkRequestDTO.photoURL(),
                drinkRequestDTO.videoURL(),
                drinkRequestDTO.ingredients(),
                drinkRequestDTO.instructions(),
                drinkRequestDTO.creatorID()
        );
    }

    /**
     * Retorna a Instância de um record DrinkResponseDTO a partir de um Drink.class
     * <p>
     * Ps: É retornado um conjunto de informações adicionais nessa instância, alocados em um record
     * UserCreatorRecipeResponseDTO, sendo informações adquiridas dentro de UserRepository.class
     *
     * @param drink Drink.class recebido
     * @return Instância DrinkResponseDTO
     */
    public DrinkResponseDTO toResponseDTO(Drink drink) {

        User creator = userRepository.findById(drink.getCreatorID()).get();

        return new DrinkResponseDTO(
                drink.getId(),
                drink.getName(),
                drink.getTypeDrink(),
                drink.getPhotoURL(),
                drink.getVideoURL(),
                drink.getIngredients(),
                drink.getInstructions(),
                new UserCreatorRecipeResponseDTO(drink.getCreatorID(), creator.getName(), creator.getEmail(), creator.getProfilePhoto())
        );
    }
}
