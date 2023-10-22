package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public UserResponseDTO save(@Valid UserSaveRequestDTO userSaveRequestDTO) {
        String encryptedPass = BCrypt.withDefaults().hashToString(12, userSaveRequestDTO.password().toCharArray());
        User newUser = userMapper.toEntity(userSaveRequestDTO);
        newUser.setPassword(encryptedPass);

        return userMapper.toResponseDTO(userRepository.save(newUser));
    }

    public void delete(String id) {
        userRepository.delete(
                userRepository.findById(id)
                        .orElseThrow(() -> new NullPointerException("User not Founded"))
        );
    }

    public UserResponseDTO get(String id) {
        return userRepository.findById(id)
                .map(userFounded -> userMapper.toResponseDTO(userFounded))
                .orElseThrow(() -> new NullPointerException("User Not Founded"));
    }

    public UserResponseDTO updateProfileSettings(String id, UserSaveRequestDTO userSaveRequestDTO) {
        return userRepository.findById(id)
                .map(userFounded -> {
                    CharSequence checkPassword = BCrypt.withDefaults().hashToString(12, userSaveRequestDTO.password().toCharArray());
                    if (!checkPassword.equals(userFounded.getPassword())) {
                        userFounded.setPassword(checkPassword);
                    }

                    userFounded.setName(userSaveRequestDTO.name());
                    userFounded.setEmail(userSaveRequestDTO.email());
                    userRepository.save(userFounded);
                    return userMapper.toResponseDTO(userFounded);
                })
                .orElseThrow(() -> new NullPointerException("User Not Founded"));
    }

    public void updateFavoriteRecipes(String id, UserFavoriteRecipesRequestDTO userFavoriteRecipes) {
        userRepository.findById(id)
                .ifPresentOrElse(userFounded -> {
                    userFounded.setFavoriteRecipes(userFavoriteRecipes.favoriteRecipes());
                    userRepository.save(userFounded);
                }, () -> new NullPointerException("User Not Founded"));
    }


    public void updateDoneRecipes(String id, UserDoneRecipesRequestDTO userDoneRecipes) {
        userRepository.findById(id)
                .ifPresentOrElse(userFounded -> {
                    userFounded.setDoneRecipes(userDoneRecipes.doneRecipes());
                    userRepository.save(userFounded);
                }, () -> new NullPointerException("User Not Founded"));
    }
}
