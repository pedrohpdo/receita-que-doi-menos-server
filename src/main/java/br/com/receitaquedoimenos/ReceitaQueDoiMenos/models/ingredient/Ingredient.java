package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Ingredient {
    @NotBlank
    private String name;

    @NotBlank
    private Integer quantity;

    @NotNull
    private Measures measure;
}
