package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.UserSaveRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.AuthenticationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Created Successfully"),
            @ApiResponse(responseCode = "409", description = "Cannot Create User. Info Conflict on Database"),
            @ApiResponse(responseCode = "422", description = "Cannot Process User Data. Arguments Not Valid")

    })
    @PostMapping("/user/new")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserSaveRequestDTO userSaveRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.createUser(userSaveRequestDTO));
    }
}
