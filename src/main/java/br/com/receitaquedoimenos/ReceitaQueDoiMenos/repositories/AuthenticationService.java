package br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserMapper;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserSaveRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;
    public UserResponseDTO save(@Valid UserSaveRequestDTO userSaveRequestDTO) {
        if (userRepository.existsByEmailAndIdNot(userSaveRequestDTO.email())) {
            throw new NullPointerException("User Already Exists"); // New UserAlreadyeExistsException exception
        }

        String encryptedPass = BCrypt.withDefaults().hashToString(12, userSaveRequestDTO.password().toCharArray());
        User newUser = userMapper.toEntity(userSaveRequestDTO);
        newUser.setPassword(encryptedPass);

        return userMapper.toResponseDTO(userRepository.save(newUser));
    }

}
