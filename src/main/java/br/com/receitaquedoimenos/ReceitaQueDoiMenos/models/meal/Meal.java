package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "recipes")
@Data
@AllArgsConstructor
public class Meal {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private TypeMeal type;
    private String photoURL;
    private String videoURL;

    @NotNull
    private ArrayList<Ingredient> ingredients;

    @NotBlank
    private String instructions;

    public Meal(String name, TypeMeal type, String photoURL, String videoURL, ArrayList<Ingredient> ingredients, String instructions) {
        this.name = name;
        this.type = type;
        this.photoURL = photoURL;
        this.videoURL = videoURL;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
}
