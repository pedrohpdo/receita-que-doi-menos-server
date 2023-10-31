package br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    Boolean existsByEmail(String email);
    Boolean existsByEmailAndIdNot(String id, String email);
    List<User> findByIdNot(String id);
}

