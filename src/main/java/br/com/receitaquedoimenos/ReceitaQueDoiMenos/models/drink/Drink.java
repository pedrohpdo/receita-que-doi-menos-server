package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * A classe Drink representa uma bebida dentro do sistema
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @since 2023.2
 */
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
    private ArrayList<String> ingredients;

    @NotBlank
    private String instructions;

    @NotBlank
    private String creatorID;

    public Drink(String name, TypeDrink type, String photoURL, String videoURL, ArrayList<String> ingredients,
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
