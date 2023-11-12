package br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * A interface UserRepository é uma extensão da interface MongoRepository, fornecendo métodos para acessar e manipular
 * dados relacionados aos usuários no banco de dados MongoDB.
 *
 * @author Pedro Henrique Pereira de Oliveira
 * @version 1.0
 * @since 2023.2
 */
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Verifica se um usuário com o e-mail especificado já existe no banco de dados.
     *
     * @param email O e-mail a ser verificado.
     * @return true se o e-mail já existe, false caso contrário.
     */
    Boolean existsByEmail(String email);

    /**
     * Verifica se existe algum usuário com o e-mail especificado, excluindo o usuário com o ID fornecido.
     *
     * @param id    O ID do usuário a ser excluído da verificação.
     * @param email O e-mail a ser verificado.
     * @return true se o e-mail já existe (excluindo o usuário com o ID fornecido), false caso contrário.
     */
    Boolean existsByEmailAndIdNot(String id, String email);

    /**
     * Recupera uma lista de usuários que têm a bebida especificada como favorita, excluindo o usuário com o ID fornecido.
     *
     * @param drinkID O ID da bebida favorita.
     * @param userID  O ID do usuário a ser excluído da lista.
     * @return Uma lista de usuários que têm a bebida especificada como favorita (excluindo o usuário com o ID fornecido).
     */
    List<User> findByFavoriteDrinksIDContainingAndIdNot(String drinkID, String userID);

    /**
     * Recupera uma lista de usuários que têm a refeição especificada como favorita, excluindo o usuário com o ID fornecido.
     *
     * @param mealID O ID da refeição favorita.
     * @param userID O ID do usuário a ser excluído da lista.
     * @return Uma lista de usuários que têm a refeição especificada como favorita (excluindo o usuário com o ID fornecido).
     */
    List<User> findByFavoriteMealsIDContainingAndIdNot(String mealID, String userID);

    /**
     * Recupera detalhes do usuário com base no e-mail.
     *
     * @param email O e-mail do usuário.
     * @return Detalhes do usuário.
     */
    UserDetails findByEmail(String email);
}

