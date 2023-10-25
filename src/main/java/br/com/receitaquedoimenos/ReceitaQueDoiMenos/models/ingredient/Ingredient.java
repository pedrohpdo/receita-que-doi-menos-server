package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient;

import lombok.Data;

@Data
public class Ingredient {
    private String name;
    private Integer quantity;
    private Measures measure;
}
