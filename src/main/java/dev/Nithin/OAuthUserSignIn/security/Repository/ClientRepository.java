package dev.Nithin.OAuthUserSignIn.security.Repository;

import java.util.Optional;
import java.util.UUID;


import dev.Nithin.OAuthUserSignIn.security.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
