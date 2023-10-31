package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequestDTO userRequestDTO) {
        return new User(
                userRequestDTO.name(),
                userRequestDTO.email(),
                userRequestDTO.password()
        );
    }

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedMeals(),
                user.getFavoriteMeals(),
                user.getCreatedDrinks(),
                user.getFavoriteDrinks()

        );
    }
}
