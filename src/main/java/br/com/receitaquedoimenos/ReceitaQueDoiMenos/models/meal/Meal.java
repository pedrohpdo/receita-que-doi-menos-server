package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.ingredient.Ingredient;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;

@Document(collection = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {

    @Id
    private String id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private TypeMeal typeMeal;

    private String photoURL;
    private String videoURL;

    @NotNull
    private ArrayList<Ingredient> ingredients;

    @NotBlank
    private String instructions;

    @NotBlank
    private String creatorID;

    public Meal(String name, TypeMeal typeMeal, String photoURL, String videoURL, ArrayList<Ingredient> ingredients,
                String instructions, String creatorID) {
        this.name = name;
        this.typeMeal = typeMeal;
        this.photoURL = photoURL;
        this.videoURL = videoURL;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.creatorID = creatorID;
    }
}
