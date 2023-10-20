package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserMapper;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        return userMapper.toResponseDTO(userRepository.save(userMapper.toEntity(userRequestDTO)));
    }

    public void delete(String id){
        userRepository.delete(
                userRepository.findById(id)
                        .orElseThrow(() -> new NullPointerException("User not Founded"))
        );
    }

    public UserResponseDTO updateProfileSettings(String id, UserRequestDTO userRequestDTO) {
        return userRepository.findById(id)
                .map(userFounded -> {
                    userFounded.setName(userRequestDTO.name());
                    userFounded.setName(userRequestDTO.email());
                    userFounded.setName(userRequestDTO.password());
                    userRepository.save(userFounded);
                    return userMapper.toResponseDTO(userFounded);
                })
                .orElseThrow(() -> new NullPointerException("User Not Founded"));
    }

    public void updateFavoriteRecipes(String id, ArrayList<String> userFavoriteRecipes) {
        userRepository.findById(id)
                .ifPresentOrElse(userFounded ->{
                    userFounded.setDoneRecipes(userFavoriteRecipes);
                    userRepository.save(userFounded);

                }, () -> new NullPointerException("User Not Founded"));
        }
    }
