package br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Representação de um Usuário dentro do Sistema
 * <p>
 * UserDetails representa uma classe que o Spring identificará como um usuário a ser autenticado,
 * essa classe simplesmente irá salvar informações do usuário, que serão encapsuladas depois em
 * objetos do tipo Authentication.
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @since 2023.2
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    private String id;

    @NotBlank
    @NonNull
    private String name;

    @NotBlank
    @NonNull
    private String email;

    @NotBlank
    @Size(min = 8, max = 12)
    @NonNull
    private String password;

    private String profilePhoto;

    private ArrayList<String> favoriteMealsID = new ArrayList<>();

    private ArrayList<String> favoriteDrinksID = new ArrayList<>();

    private ArrayList<String> createdMealsID = new ArrayList<>();

    private ArrayList<String> createdDrinksID = new ArrayList<>();


    public User(String userID) {
        this.id = userID;
    }

    public User(String name, String email, String password, String profilePhoto) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePhoto = profilePhoto;
    }

    /**
     * @return uma Collection(List) de permissões que o usuário tem dentro da aplicação
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
