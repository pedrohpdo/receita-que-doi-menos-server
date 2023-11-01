package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;

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

@Document(collection = "drinks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drink {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private TypeDrink typeDrink;

    private String photoURL;
    private String videoURL;

    @NotNull
    private ArrayList<Ingredient> ingredients;

    @NotBlank
    private String instructions;

    @NotBlank
    private String creatorID;

    public Drink(String name, TypeDrink type, String photoURL, String videoURL, ArrayList<Ingredient> ingredients,
                 String instructions, String creatorID) {
        this.name = name;
        this.typeDrink = type;
        this.photoURL = photoURL;
        this.videoURL = videoURL;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.creatorID = creatorID;
    }
}
