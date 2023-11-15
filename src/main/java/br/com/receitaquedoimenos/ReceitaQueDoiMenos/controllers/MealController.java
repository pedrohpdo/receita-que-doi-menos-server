package br.com.receitaquedoimenos.ReceitaQueDoiMenos.controllers;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.handler.ErrorResponse;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.MealResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.TypeMeal;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.MealService;
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
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas a refeições na aplicação "ReceitaQueDoiMenos".
 */
@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    MealService mealService;

    /**
     * Cria uma nova instância de uma refeição.
     *
     * @param mealRequestDTO Informações básicas para criar uma refeição.
     * @return ResponseEntity contendo os dados da refeição criada e o status HTTP 201 (Created).
     */
    @Operation(summary = "Criar uma nova instância de uma refeição")
    @Parameter(
            name = "mealRequestDTO",
            description = "Informações básicas para criar refeição",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealRequestDTO.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Refeição Criada com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MealResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Criação não Autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Falha ao Registrar, Conflito de Informações", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Usuário(Creator) não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Palavra Proibida Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @PostMapping
    public ResponseEntity<MealResponseDTO> createMeal(@RequestBody @Valid MealRequestDTO mealRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(mealService.createMeal(mealRequestDTO));
    }

    /**
     * Retorna todas as refeições registradas.
     *
     * @return ResponseEntity contendo a lista de todas as refeições e o status HTTP 200 (OK).
     */
    @Operation(summary = "Retorna todos as Refeições registrados")
    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MealResponseDTO.class))})
    })
    @GetMapping("/all")
    public ResponseEntity<List<MealResponseDTO>> getAllMeals(){
        return ResponseEntity.ok(mealService.getAll());
    }

    /**
     * Retorna a(s) refeição(ões) pelo seu tipo.
     *
     * @param typeMeal Tipo da refeição.
     * @return ResponseEntity contendo a lista de refeições do tipo especificado e o status HTTP 200 (OK).
     */
    @Operation(summary = "Retorna a(s) refeição(ões) pelo seu tipo")
    @Parameter(
            name = "typeMeal",
            description = "Tipo da refeição",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeMeal.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MealResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @Transactional
    @GetMapping("/byType/{typeMeal}")
    public ResponseEntity<List<MealResponseDTO>> getRecipesByTypeMeal(@PathVariable TypeMeal typeMeal) {
        return ResponseEntity.ok(mealService.getMealsByTypeMeal(typeMeal));
    }

    /**
     * Retorna a(s) refeição(ões) pelo seu nome.
     *
     * @param name Nome da refeição.
     * @return ResponseEntity contendo a lista de refeições com o nome especificado e o status HTTP 200 (OK).
     */
    @Operation(summary = "Retorna a(s) refeição(ões) pelo seu nome")
    @Parameter(
            name = "mealName",
            description = "Nome da refeição",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MealResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @Transactional
    @GetMapping("byName/{name}")
    public ResponseEntity<List<MealResponseDTO>> getMealsByName(@PathVariable String name){
        return ResponseEntity.ok(mealService.getMealsByName(name));
    }

    /**
     * Retorna uma refeição pelo seu ID.
     *
     * @param mealID ID da refeição.
     * @return ResponseEntity contendo os dados da refeição encontrada e o status HTTP 200 (OK).
     */
    @Operation(summary = "Retorna uma refeição pelo seu ID")
    @Parameter(
            name = "mealID",
            description = "ID da refeição",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Retornados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MealResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Dados não Encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @GetMapping("byMealID/{mealID}")
    public ResponseEntity<MealResponseDTO> getMealsById(@PathVariable String mealID){
        return ResponseEntity.ok(mealService.getMealsById(mealID));
    }

    @Operation(summary = "Atualiza as Informações de uma Refeição")
    @Parameter(
            name = "mealID",
            description = "ID da Refeição",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "userID",
            description = "ID do Usuário Criador",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "mealRequestDTO",
            description = "Novas Informações sobre a Refeição",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MealRequestDTO.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados Atualizados com Sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MealResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Dados sobre a refeição não encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Operação não Autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Palavra Proibida Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @PutMapping("/{mealID}/{userID}")
    public ResponseEntity<MealResponseDTO> updateRecipe(@PathVariable String mealID, @PathVariable String userID,
                                                        @RequestBody @Valid MealRequestDTO mealRequestDTO) {
        return ResponseEntity.ok(mealService.updateMealInfo(mealID, userID, mealRequestDTO));
    }

    @Operation(summary = "Deletar Registro de Uma Refeição")
    @Parameter(
            name = "mealID",
            description = "ID da Refeição",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @Parameter(
            name = "userID",
            description = "ID do Usuário Criador",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dados Apagados com Sucesso. Sem Conteúdo."),
            @ApiResponse(responseCode = "404", description = "Refeição não Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Operação Não Autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Palavra Proibida Encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @Transactional
    @DeleteMapping("/{mealID}/{userID}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String mealID,  @PathVariable String userID) {
        mealService.deleteMeal(mealID, userID);
        return ResponseEntity.noContent().build();
    }
}
