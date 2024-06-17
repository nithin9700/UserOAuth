package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.entity.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserResponseDTO(String name, String email, Set<String> roles, String phone, UUID id) {
    public static UserResponseDTO fromUser(User user) {
        Set<Role> roles = user.getRoles();
        Set<String> roleName= roles.stream().map(Role::getName).collect(Collectors.toSet());

        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getUsername(), user.getEmail(), roleName, user.getPhoneNumber(), user.getId());
        return userResponseDTO;
    }
}
