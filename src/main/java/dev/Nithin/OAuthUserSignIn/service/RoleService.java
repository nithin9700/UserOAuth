package dev.Nithin.OAuthUserSignIn.service;

import dev.Nithin.OAuthUserSignIn.DTO.RoleDTO;
import dev.Nithin.OAuthUserSignIn.DTO.RoleRequestDTO;

public interface RoleService {
    RoleDTO createRole(RoleRequestDTO  roleRequestDTO);
}
