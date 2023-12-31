package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal;

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

/**
 * A classe Meal representa uma refeição dentro do sistema
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @since 2023.2
 */
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
    private ArrayList<String> ingredients;

    @NotBlank
    private String instructions;

    @NotBlank
    private String creatorID;

    /**
     * Contrução de uma nova instância de uma refeição dentro do sistema
     *
     * @param name         String referente ao nome
     * @param typeMeal     Enum referente ao tipo da refeição
     * @param photoURL     String URL de uma imagem
     * @param videoURL     String URL de um vídeo
     * @param ingredients  ArrayList contendo os ingredientes
     * @param instructions String referente ao modo de preparo
     * @param creatorID    String referente ao criador da refeição
     */
    public Meal(String name, TypeMeal typeMeal,
                String photoURL, String videoURL,
                ArrayList<String> ingredients,
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
