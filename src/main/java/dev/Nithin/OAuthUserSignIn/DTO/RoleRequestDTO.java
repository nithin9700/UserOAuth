package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.Role;

public record RoleRequestDTO(String name, String description) {

    public static Role from(RoleRequestDTO roleRequestDTO){
        Role role = new Role();
        role.setDescription(roleRequestDTO.description);
        role.setName(roleRequestDTO.name);
        return role;
    }
}
