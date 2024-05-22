package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.entity.User;
import java.util.Set;

public record UserResponseDTO(String name, String email, Set<Role> roles, String phone) {
    public static UserResponseDTO fromUser(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getUsername(), user.getEmail(), user.getRoles(), user.getPhoneNumber());
        return userResponseDTO;
    }
}
