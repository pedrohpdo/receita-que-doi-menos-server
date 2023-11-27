package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataAlreadyExistsException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.TokenException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.LoginResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.RefreshTokenRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.RefreshTokenResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.LoginRequestDTO;
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
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Registro de um novo usuário no sistema. Novos usuários terão suas senhas encriptadas dentro do sistema por
     * razões de seguranaça
     * <p>
     * Todas as informações enviadas pelo UserRequestDTO serão validadas no filtro de palavras de palavras proibidas
     *
     * @param userRequestDTO Informações requeridas para um cadastro de usuário
     * @return uma UserResponseDTO com informações de cadastro
     * @throws DataAlreadyExistsException caso já exista um email igual cadastrado no sistema
     */
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.email()))
            throw new DataAlreadyExistsException("User Already Exists");

        validator.validateUserData(userRequestDTO);
        String passwordEncoded = passwordEncoder.encode(userRequestDTO.password());

        return userMapper.toResponseDTO(userRepository.save(new User(userRequestDTO.name(), userRequestDTO.email(), passwordEncoded)));
    }

    /**
     * Login do usuário dentro do sistema. As informações do Login devem estar devidamente autenticadas para serem
     * validadas
     *
     * @param loginRequestDTO       Informações requeridas para o login do usuário
     * @param authenticationManager Componente de autênticação já fornecessido no UserController
     * @return Um JSON contendo tokens de acesso caso as informações do usuário estejam validadas
     */
    @Transactional
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO, AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());
        String refreshToken = tokenService.generateRefreshToken((User) auth.getPrincipal());

        return new LoginResponseDTO(token, refreshToken);
    }

    /**
     * Validação de um refresh token.
     * <p>
     * Usuário logados possuem um access_token e um refresh_token. Os refresh tokens devem ser enviados próximo ao prazo
     * de expiração do access_token para que um novo seja gerado.
     *
     * @param request Formulário de requisição contendo um token de autorização
     * @return Um novo access_token
     */
    @Transactional
    public RefreshTokenResponseDTO validateRefreshToken(RefreshTokenRequestDTO request) {

        String emailExtracted = tokenService.validateToken(request.refreshToken());
        User userDetails = (User) userRepository.findByEmail(emailExtracted);

        if (userDetails != null) {
            return new RefreshTokenResponseDTO(tokenService.generateToken(userDetails));
        }
        throw new TokenException("User Details Not Founded on Token ");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }
}
