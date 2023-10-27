package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.recipe.Recipe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@Data
public class User {
    @Id
    private String id;

    @NotBlank
    @NonNull
    private String name;

    @NotBlank
    @NonNull
    private String email;

    @NotBlank
    @Size(min = 8)
    @NonNull
    private String password;

    private ArrayList<Recipe> favoriteRecipes;
    private ArrayList<Recipe> doneRecipes;

}
