package dev.Nithin.OAuthUserSignIn.repository;

import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
