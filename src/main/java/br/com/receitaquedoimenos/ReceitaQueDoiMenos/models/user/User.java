package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;
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
    @Min(8)
    @NonNull
    private String password;

    private ArrayList<String> favoriteRecipes;
    private ArrayList<String> doneRecipes;

}
