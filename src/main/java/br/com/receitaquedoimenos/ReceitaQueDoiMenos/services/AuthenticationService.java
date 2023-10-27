package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataAlreadyExistsException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserMapper;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserSaveRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public UserResponseDTO createUser(@Valid UserSaveRequestDTO userSaveRequestDTO) {
        if (userRepository.existsByEmail(userSaveRequestDTO.email())) {
            throw new DataAlreadyExistsException("User Already Exists"); //
        }
        //encript password
        return userMapper.toResponseDTO(userRepository.save(userMapper.toEntity(userSaveRequestDTO)));
    }

}
