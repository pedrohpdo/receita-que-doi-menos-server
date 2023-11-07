package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.LoginResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.login.RefreshTokenResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserLoginDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.AuthenticationService;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Created"),
            @ApiResponse(responseCode = "403", description = "Forbidden Word Founded"),
            @ApiResponse(responseCode = "409", description = "Already Exists An Email on System")

    })
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerUser(userRequestDTO));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Logged"),
            @ApiResponse(responseCode = "403", description = "Forbidden Word Founded"),
            @ApiResponse(responseCode = "409", description = "Already Exists An Email on System")
    })
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authenticationService.loginUser(userLoginDTO, authenticationManager));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Logged"),
            @ApiResponse(responseCode = "403", description = "Forbidden Word Founded"),
            @ApiResponse(responseCode = "409", description = "Already Exists An Email on System")
    })
    @Transactional
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.validateRefreshToken(request));
    }
}
