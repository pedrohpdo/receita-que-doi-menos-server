package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Representação de cada ingrediente presente dentro de cada receita
 *
 * @author Pedro Henrique Pereira de Oliveira
 */
@Data
public class Ingredient {

    @NotBlank
    private String name;

    @NotBlank
    private Integer quantity;

    @NotNull
    private Measures measure;
}
