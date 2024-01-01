package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public interface AuthUserDAO {

    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    UserEntity getUser(String username);
    UserEntity getUserById(UUID userId);
    int createUser(UserEntity user);
    UserEntity updateUser(UserEntity user);
    void deleteUserById(UUID userId);
}
