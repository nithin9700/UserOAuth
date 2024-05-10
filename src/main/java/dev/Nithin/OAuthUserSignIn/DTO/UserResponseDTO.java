package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.entity.User;
import org.springframework.jdbc.UncategorizedSQLException;

import java.util.List;

public record UserResponseDTO(String name, String email, List<Role> roles) {
    public static UserResponseDTO fromUser(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getUsername(), user.getEmail(), user.getRoles());
        return userResponseDTO;
    }
}
