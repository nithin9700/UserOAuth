package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.entity.User;
import org.springframework.jdbc.UncategorizedSQLException;

import java.util.List;

public record UserResponseDTO(String name, String email, List<Role> roles, String phone, String token) {
    public static UserResponseDTO fromUser(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getUsername(), user.getEmail(), user.getRoles(), user.getPhoneNumber(), user.getToken());
        return userResponseDTO;
    }
}
