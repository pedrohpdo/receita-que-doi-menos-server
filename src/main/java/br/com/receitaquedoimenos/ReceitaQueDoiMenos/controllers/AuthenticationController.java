package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.handler.ErrorResponse;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.LoginResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.RefreshTokenRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.RefreshTokenResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.LoginRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gerenciar operações relacionadas à autenticação na aplicação "ReceitaQueDoiMenos".
 */
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal Server Error"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
})
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * Cadastro de um novo usuário.
     *
     * @param userRequestDTO Informações básicas para criar um novo usuário.
     * @return ResponseEntity contendo os dados do usuário registrado e o status HTTP 201 (Created).
     */
    @Operation(summary = "Cadastro de um Novo Usuário")
    @Parameter(
            name = "userRequestDTO",
            description = "Informações Básicas para Criar um novo Usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário Registrado com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "403", description = "Palavra Proibida Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Operação não Autorizada. Conflito de Dados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})

    })
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerUser(userRequestDTO));
    }


    /**
     * Login de um usuário existente.
     *
     * @param loginRequestDTO Informações cadastrais do usuário para login.
     * @return ResponseEntity contendo os dados do usuário logado e o status HTTP 200 (OK).
     */
    @Operation(summary = "Login de um novo Usuário")
    @Parameter(
            name = "userLoginDTO",
            description = "Informações Cadastrais do Usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequestDTO.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Logado com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class))}),
            @ApiResponse(responseCode = "403", description = "Palavra Proibida Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Operação não Autorizada. Conflito de Dados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Problema com a geração do Token de Acesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authenticationService.loginUser(loginRequestDTO, authenticationManager));
    }

    /**
     * Gera um novo Token de Acesso usando um Refresh Token.
     *
     * @param bodyRequest Refresh Token contido no corpo da requisição.
     * @return ResponseEntity contendo o novo Token de Acesso e o status HTTP 200 (OK).
     */
    @Operation(summary = "Gera um Novo Token de Acesso", description = "Deve Receber um Refresh Token para Retornar um novo Token de Acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token Validado com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Problema com a geração do Token de Acesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @Parameter(
            name = "Refresh Token",
            description = "Refresh Token Contido no Body da Requisição",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenRequestDTO.class)))
    @Transactional
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO bodyRequest) {
        return ResponseEntity.ok(authenticationService.validateRefreshToken(bodyRequest));
    }
}
