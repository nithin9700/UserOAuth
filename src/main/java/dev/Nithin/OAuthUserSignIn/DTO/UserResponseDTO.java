package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.entity.User;
import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(String name, String email, Set<Role> roles, String phone, UUID id) {
    public static UserResponseDTO fromUser(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getUsername(), user.getEmail(), user.getRoles(), user.getPhoneNumber(), user.getId());
        return userResponseDTO;
    }
}
