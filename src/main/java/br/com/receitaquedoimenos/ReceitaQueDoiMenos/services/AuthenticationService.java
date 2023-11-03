package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataAlreadyExistsException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.TokenException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.LoginResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.RefreshTokenResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils.ForbiddenWordsValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    ForbiddenWordsValidator validator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserMapper userMapper;


    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.email()))
            throw new DataAlreadyExistsException("User Already Exists");

        validator.validateUserData(userRequestDTO);
        String passwordEncoded = passwordEncoder.encode(userRequestDTO.password());

        return userMapper.toResponseDTO(userRepository.save(new User(userRequestDTO.name(), userRequestDTO.email(), passwordEncoded)));
    }

    public LoginResponseDTO loginUser(UserLoginDTO userLoginDTO, AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(userLoginDTO.email(), userLoginDTO.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());
        String refreshToken = tokenService.generateRefreshToken((User) auth.getPrincipal());

        return new LoginResponseDTO(token, refreshToken);
    }

    public RefreshTokenResponseDTO validateRefreshToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationHeader != null) {
            String refreshToken = authenticationHeader.replace("Bearer ", "");

            String emailExtracted = tokenService.validateToken(refreshToken);
            User userDetails = (User) userRepository.findByEmail(emailExtracted);

            if (userDetails != null) {
                return new RefreshTokenResponseDTO(tokenService.generateToken(userDetails));
            }
        }
        throw new TokenException("User Details Not Founded on Token ");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }
}
