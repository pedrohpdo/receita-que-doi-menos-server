package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserSaveRequestDTO userSaveRequestDTO) {
        return new User(
                userSaveRequestDTO.name(),
                userSaveRequestDTO.email(),
                userSaveRequestDTO.password()
        );
    }

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getFavoriteRecipes(),
                user.getDoneRecipes()
        );
    }
}
