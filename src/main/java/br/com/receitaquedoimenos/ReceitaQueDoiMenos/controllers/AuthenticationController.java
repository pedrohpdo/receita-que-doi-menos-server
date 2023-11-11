package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.handler.ErrorResponse;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.LoginResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.RefreshTokenResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserLoginDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
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

    @Operation(summary = "Login de um novo Usuário")
    @Parameter(
            name = "userLoginDTO",
            description = "Informações Cadastrais do Usuário",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginDTO.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Logado com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class))}),
            @ApiResponse(responseCode = "403", description = "Palavra Proibida Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Operação não Autorizada. Conflito de Dados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Problema com a geração do Token de Acesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authenticationService.loginUser(userLoginDTO, authenticationManager));
    }


    @Operation(summary = "Gera um Novo Token de Acesso", description = "Deve Receber um Refresh Token para Retornar um novo Token de Acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token Validado com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Problema com a geração do Token de Acesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @Parameter(
            name = "Refresh Token",
            description = "Refresh Token Contido no Formulário de Request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpServletRequest.class)))
    @Transactional
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.validateRefreshToken(request));
    }
}
