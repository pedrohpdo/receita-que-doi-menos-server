package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import org.springframework.stereotype.Component;

/**
 * Classe que Representa um mapeador de entidades, melhorando a abstração dentro dos serviços.
 *
 * @author Pedro Henrique Pereira de Oliveira
 */
@Component
public class UserMapper {

    /**
     * Retorna uma instância da User.class a partir de um record UserRequestDTO
     *
     * @param userRequestDTO DTO recebido
     * @return Instância Meal.class
     */
    public User toEntity(UserRequestDTO userRequestDTO) {
        return new User(
                userRequestDTO.name(),
                userRequestDTO.email(),
                userRequestDTO.password()
        );
    }

    /**
     * Retorna a Instância de um record MealResponseDTO a partir de um Meal.class
     * <p>
     *
     * @param user User.class recebido
     * @return Instância UserResponseDTO
     */
    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedMealsID(),
                user.getFavoriteMealsID(),
                user.getCreatedDrinksID(),
                user.getFavoriteDrinksID()

        );
    }
}
