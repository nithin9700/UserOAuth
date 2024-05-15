package dev.Nithin.OAuthUserSignIn.repository;

import dev.Nithin.OAuthUserSignIn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String Email);
    Optional<User> findByToken(String token);
}
