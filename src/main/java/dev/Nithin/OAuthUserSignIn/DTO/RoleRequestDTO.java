package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.Role;

import java.util.Locale;

public record RoleRequestDTO(String name, String description) {

    public static Role from(RoleRequestDTO roleRequestDTO){
        Role role = new Role();
        role.setDescription(roleRequestDTO.description.toLowerCase());
        role.setName(roleRequestDTO.name.toLowerCase());
        return role;
    }
}
