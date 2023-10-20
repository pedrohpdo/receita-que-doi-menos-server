package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseDTO(
        String id,
        String name,
        String email,
        ArrayList<String> favoriteRecipes,
        ArrayList<String> doneRecipes
) {}

