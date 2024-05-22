package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.entity.User;

public record RoleDTO(String role, String description){
    public static RoleDTO fromRoleLoginRequestDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO(role.getName(), role.getDescription());
        return roleDTO;
    }
}
