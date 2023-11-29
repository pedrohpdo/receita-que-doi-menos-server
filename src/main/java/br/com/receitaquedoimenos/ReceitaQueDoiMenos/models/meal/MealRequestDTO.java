package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

/**
 * Informações referentes a um drink criados ou atualizados requeridos dentro do sistema
 *
 * @param name         String referente ao nome
 * @param typeMeal     Enum referente ao tipo da refeição
 * @param photoURL     String URL de uma imagem
 * @param videoURL     String URL de um vídeo
 * @param ingredients  ArrayList contendo os ingredientes
 * @param instructions String referente ao modo de preparo
 * @param creatorID    String referente ao criador da refeição
 * @author Pedro Henrique Pereira de Oliveira
 */
public record MealRequestDTO(
        @NotBlank String name,
        TypeMeal typeMeal,
        @NotBlank String photoURL,
        String videoURL,
        @NotNull @NotEmpty ArrayList<String> ingredients,
        @NotBlank String instructions,
        @NotBlank String creatorID
) {
}
