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
                user.getCreatedMealsID(),
                user.getFavoriteMealsID(),
                user.getCreatedDrinksID(),
                user.getFavoriteDrinksID()

        );
    }
}
