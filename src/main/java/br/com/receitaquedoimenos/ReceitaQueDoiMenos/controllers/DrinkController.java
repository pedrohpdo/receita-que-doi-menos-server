package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.handler.ErrorResponse;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.TypeDrink;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.DrinkService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas a Drinks na aplicação "ReceitaQueDoiMenos".
 */
@RestController
@RequestMapping("/drinks")
public class DrinkController {

    @Autowired
    DrinkService drinkService;

    /**
     * Cria uma nova instância de um Drink.
     *
     * @param drinkRequestDTO Informações básicas para criar um Drink.
     * @return ResponseEntity contendo os dados do Drink criado e o status HTTP 201 (Created).
     */

    @Operation(summary = "Criar uma nova instância de uma Drink")
    @Parameter(
            name = "drinkRequestDTO",
            description = "Informações Básicas para criar um Drink",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkRequestDTO.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Data successfully registered", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DrinkResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized to Create New Data", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Failed to register. Information Conflict", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User Not Found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden Word Founded", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @PostMapping
    public ResponseEntity<DrinkResponseDTO> createDrink(@RequestBody @Valid DrinkRequestDTO drinkRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(drinkService.createDrink(drinkRequestDTO));
    }

    /**
     * Retorna um Drink pelo seu ID.
     *
     * @param drinkID ID do Drink.
     * @return ResponseEntity contendo os dados do Drink encontrado e o status HTTP 200 (OK).
     */
    @Operation(summary = "Retorna um Drinks pelo seu ID")
    @Parameter(
            name = "drinkID",
            description = "ID do Drink",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DrinkResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @GetMapping("/byId/{drinkID}")
    public ResponseEntity<DrinkResponseDTO> getDrinkById(@PathVariable String drinkID) {
        return ResponseEntity.ok(drinkService.getDrinkById(drinkID));
    }

    /**
     * Retorna todos os Drinks registrados.
     *
     * @return ResponseEntity contendo uma lista de Drinks e o status HTTP 200 (OK).
     */
    @Operation(summary = "Retorna todos os Drinks Registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DrinkResponseDTO.class))}),
    })
    @Transactional
    @GetMapping("/all")
    public ResponseEntity<List<DrinkResponseDTO>> getAllDrinks() {
        return ResponseEntity.ok(drinkService.getAllDrinks());
    }

    /**
     * Retorna o(s) drink(s) pelo seu tipo.
     *
     * @param typeDrink Tipo do Drink.
     * @return ResponseEntity contendo uma lista de Drinks do tipo especificado e o status HTTP 200 (OK).
     */
    @Operation(summary = "Retorna o(s) drink(s) pelo seu tipo")
    @Parameter(
            name = "typeDrink",
            description = "Tipo do Drink",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeDrink.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DrinkResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @Transactional
    @GetMapping("/byType/{typeDrink}")
    public ResponseEntity<List<DrinkResponseDTO>> getDrinksByType(@PathVariable TypeDrink typeDrink) {
        return ResponseEntity.ok(drinkService.getDrinksByType(typeDrink));
    }

    /**
     * Retorna o(s) drink(s) pelo seu nome.
     *
     * @param drinkName Nome do Drink.
     * @return ResponseEntity contendo uma lista de Drinks com o nome especificado e o status HTTP 200 (OK).
     */
    @Operation(summary = "Retorna o(s) drink(s) pelo seu nome")
    @Parameter(
            name = "drinkName",
            description = "Nome do Drink",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DrinkResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @Transactional
    @GetMapping("/byName/{drinkName}")
    public ResponseEntity<List<DrinkResponseDTO>> getDrinksByName(@PathVariable String drinkName) {
        return ResponseEntity.ok(drinkService.getDrinksByName(drinkName));

    }

    /**
     * Atualiza as informações de um Drink.
     *
     * @param drinkID           ID do Drink.
     * @param userID            ID do Usuário Criador.
     * @param drinkRequestDTO   Novas informações sobre o Drink.
     * @return ResponseEntity contendo os dados do Drink atualizado e o status HTTP 200 (OK).
     */
    @Operation(summary = "Atualiza as Informações de um Drink")
    @Parameter(
            name = "drinkID",
            description = "ID do Drink",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "userID",
            description = "ID do Usuário Criador",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "drinkRequestDTO",
            description = "Novas Informações sobre o Drink",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrinkRequestDTO.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Atualizados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DrinkResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Dados sobre o Drink não encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Operação não Autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Palavra Proibida Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @PutMapping("/{drinkID}/{userID}")
    public ResponseEntity<DrinkResponseDTO> updateDrink(@PathVariable String drinkID, @PathVariable String userID, @RequestBody @Valid DrinkRequestDTO drinkRequestDTO) {
        return ResponseEntity.ok(drinkService.updateDrink(drinkID, userID, drinkRequestDTO));
    }

    /**
     * Deleta o registro de um Drink.
     *
     * @param drinkID ID do Drink.
     * @param userID  ID do Usuário Criador.
     * @return ResponseEntity sem conteúdo e o status HTTP 204 (No Content) após a exclusão bem-sucedida.
     */
    @Operation(summary = "Deletar Registro de um Drink")
    @Parameter(
            name = "drinkID",
            description = "ID do Drink",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "userID",
            description = "ID do Usuário Criador",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dados Apagados com Sucesso. Sem Conteúdo."),
            @ApiResponse(responseCode = "404", description = "Drink não Encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Operação não Autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Palavra Proibida Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @DeleteMapping("/{drinkID}/{userID}")
    public ResponseEntity<Void> deleteDrink(@PathVariable String drinkID, @PathVariable String userID) {
        drinkService.deleteDrink(drinkID, userID);
        return ResponseEntity.noContent().build();
    }
}
